package com.example.repository

import com.example.data.local.LessonDao
import com.example.data.local.PaymentDao
import com.example.data.local.UserDao
import com.example.data.model.LessonEntity
import com.example.data.model.PaymentReceiptEntity
import com.example.data.model.PaymentStatus
import com.example.data.model.SubscriptionPlan
import com.example.data.model.UserEntity
import com.example.data.remote.ApiContent
import com.example.data.remote.ApiPart
import com.example.data.remote.ApiRequest
import com.example.data.remote.GeminiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class LessonRepository(private val lessonDao: LessonDao) {
    val allLessons: Flow<List<LessonEntity>> = lessonDao.getAllLessonsFlow()

    fun getLessonsForLevel(level: String): Flow<List<LessonEntity>> {
        return lessonDao.getLessonsByLevelFlow(level)
    }

    suspend fun getLessonById(id: Int): LessonEntity? {
        return lessonDao.getLessonById(id)
    }

    suspend fun completeLesson(id: Int) {
        lessonDao.markLessonCompleted(id)
    }
}

class UserRepository(private val userDao: UserDao) {
    val userFlow: Flow<UserEntity?> = userDao.getUserFlow()

    suspend fun getUser(): UserEntity? = userDao.getUser()

    suspend fun addXpAndProgress(xp: Int) {
        userDao.addXpAndLesson(xp)
    }

    suspend fun updateSubscription(planName: String) {
        val thirtyDaysMillis = 30L * 24L * 60L * 60L * 1000L
        val expiry = System.currentTimeMillis() + thirtyDaysMillis
        userDao.updateSubscription(planName, expiry)
    }
}

class PaymentRepository(
    private val paymentDao: PaymentDao,
    private val userDao: UserDao
) {
    val allReceipts: Flow<List<PaymentReceiptEntity>> = paymentDao.getAllReceiptsFlow()

    suspend fun submitPaymentReceipt(
        planRequested: String,
        amountTzs: Int,
        mpesaRef: String,
        phoneNumber: String,
        receiptImageUri: String?
    ): Long {
        val receipt = PaymentReceiptEntity(
            planRequested = planRequested,
            amountTzs = amountTzs,
            mpesaRef = mpesaRef,
            phoneNumber = phoneNumber,
            receiptImageUri = receiptImageUri,
            status = PaymentStatus.PENDING.name,
            submittedAtTimestamp = System.currentTimeMillis()
        )
        return paymentDao.insertReceipt(receipt)
    }

    suspend fun verifyReceiptByAdmin(receiptId: Long, approve: Boolean, adminNotes: String) {
        val status = if (approve) PaymentStatus.APPROVED.name else PaymentStatus.REJECTED.name
        val now = System.currentTimeMillis()
        paymentDao.updateReceiptStatus(receiptId, status, now, adminNotes)

        if (approve) {
            val receipts = paymentDao.getAllReceiptsFlow().firstOrNull()
            val match = receipts?.find { it.id == receiptId }
            if (match != null) {
                val thirtyDays = 30L * 24L * 60L * 60L * 1000L
                userDao.updateSubscription(match.planRequested, now + thirtyDays)
            }
        }
    }
}

class AiTutorRepository {
    private val systemPrompt = """
        You are Mwalimu AI, a friendly German language tutor specifically teaching Tanzanian students.
        - Explain German grammar and vocabulary using Swahili as the primary language of instruction.
        - Give clear examples in German with Swahili translations.
        - Offer helpful Swahili analogies (e.g. comparing German articles der/die/das or cases to Swahili noun classes A-WA, KI-VI).
        - Keep responses concise, structured, and encouraging!
    """.trimIndent()

    suspend fun askTutor(userMessage: String, conversationHistory: List<Pair<String, String>>): String {
        val apiKey = GeminiClient.apiKey
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return generateOfflineAiResponse(userMessage)
        }

        return try {
            val contents = mutableListOf<ApiContent>()
            conversationHistory.takeLast(6).forEach { (user, ai) ->
                contents.add(ApiContent(role = "user", parts = listOf(ApiPart(text = user))))
                contents.add(ApiContent(role = "model", parts = listOf(ApiPart(text = ai))))
            }
            contents.add(ApiContent(role = "user", parts = listOf(ApiPart(text = userMessage))))

            val request = ApiRequest(
                contents = contents,
                systemInstruction = ApiContent(parts = listOf(ApiPart(text = systemPrompt)))
            )

            val response = GeminiClient.service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "Samahani, sijapata jibu kwa sasa. Jaribu tena au uliza swali jingine!"
        } catch (e: Exception) {
            generateOfflineAiResponse(userMessage)
        }
    }

    private fun generateOfflineAiResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("kujitambulisha") || lower.contains("heißen") ->
                "🇩🇪 **Kujitambulisha (Sich vorstellen)**:\n" +
                        "Kwa Kijerumani unapojitambulisha unasema:\n" +
                        "- *Ich heiße Baraka.* (Naitwa Baraka)\n" +
                        "- *Ich komme aus Tansania.* (Ninatoka Tanzania)\n" +
                        "- *Ich wohne in Dar es Salaam.* (Ninakaa Dar es Salaam)\n\n" +
                        "💡 **Funzo la Kiswahili**: Kitenzi *heißen* hubadilika kulingana na nafsi: *Ich heiße*, *Du heißt*, *Er/Sie heißt*."

            lower.contains("der") || lower.contains("die") || lower.contains("das") || lower.contains("artikel") ->
                "🇩🇪 **Jinsia za Majina (Artikel)**:\n" +
                        "Kijerumani kina aina 3 za majina (sawa na ngeli za Kiswahili):\n" +
                        "- ♂️ **der** (Kiume) - Mfano: *der Vater* (baba), *der Tisch* (meza)\n" +
                        "- ♀️ **die** (Kike) - Mfano: *die Mutter* (mama), *die Tasche* (mfuko)\n" +
                        "- ⚪ **das** (Kati) - Mfano: *das Kind* (mtoto), *das Buch* (kitabu)\n\n" +
                        "Kumbuka: Kila jina lazima uhifadhi pamoja na Artikel yake!"

            lower.contains("kahawa") || lower.contains("mkahawa") || lower.contains("kaffee") ->
                "☕ **Kuagiza Mkahawani**:\n" +
                        "Semeni kwa heshima:\n" +
                        "- *Ich möchte einen Kaffee, bitte.* (Ningependa kahawa moja, tafadhali)\n" +
                        "- *Die Rechnung, bitte!* (Bili tafadhali!)\n" +
                        "- *Danke schön!* (Asante sana!)"

            else ->
                "Jambo! Mimi ni **Mwalimu AI wa Kijerumani**. Ninaweza kukusaidia kujifunza msamiati, sarufi (grammar), salamu, au kuandaa usahili wa kazi nchini Ujerumani au Tanzania!\n\n" +
                        "Jaribu kuniuliza:\n" +
                        "1. *Nieleze jinsi ya kusema 'Habari za asubuhi'*?\n" +
                        "2. *Tofauti kati ya 'Du' na 'Sie' ni ipi?*\n" +
                        "3. *Nambari 1 hadi 10 kwa Kijerumani.*"
        }
    }
}
