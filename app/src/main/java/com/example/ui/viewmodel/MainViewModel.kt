package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.LessonEntity
import com.example.data.model.PaymentReceiptEntity
import com.example.data.model.SubscriptionPlan
import com.example.data.model.UserEntity
import com.example.repository.AiTutorRepository
import com.example.repository.LessonRepository
import com.example.repository.PaymentRepository
import com.example.repository.UserRepository
import com.example.util.TextToSpeechHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ChatMessage(val sender: String, val text: String, val timestamp: Long = System.currentTimeMillis())

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    val lessonRepository = LessonRepository(db.lessonDao())
    val userRepository = UserRepository(db.userDao())
    val paymentRepository = PaymentRepository(db.paymentDao(), db.userDao())
    val aiTutorRepository = AiTutorRepository()
    private val ttsHelper = TextToSpeechHelper(application)

    val userState: StateFlow<UserEntity?> = userRepository.userFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val allLessonsState: StateFlow<List<LessonEntity>> = lessonRepository.allLessons.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allReceiptsState: StateFlow<List<PaymentReceiptEntity>> = paymentRepository.allReceipts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Chat State
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage("ai", "Hujambo! Karibu katika Mwalimu AI wa Kijerumani! 🇩🇪🇹🇿 Ninawezaje kukusaidia leo?")
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    // Selected Level for Catalog (A1, A2, B1, B2)
    private val _selectedLevel = MutableStateFlow("A1")
    val selectedLevel: StateFlow<String> = _selectedLevel.asStateFlow()

    // Selected Active Lesson for viewing
    private val _activeLessonId = MutableStateFlow<Int?>(1)
    val activeLessonId: StateFlow<Int?> = _activeLessonId.asStateFlow()

    // Payment Submission Form State
    private val _paymentSubmissionSuccess = MutableStateFlow<String?>(null)
    val paymentSubmissionSuccess: StateFlow<String?> = _paymentSubmissionSuccess.asStateFlow()

    fun setSelectedLevel(level: String) {
        _selectedLevel.value = level
    }

    fun setActiveLessonId(lessonId: Int) {
        _activeLessonId.value = lessonId
    }

    fun speakGerman(text: String) {
        ttsHelper.speakGerman(text)
    }

    fun completeLessonAndAwardXp(lessonId: Int, xp: Int = 50) {
        viewModelScope.launch {
            lessonRepository.completeLesson(lessonId)
            userRepository.addXpAndProgress(xp)
        }
    }

    fun sendMessageToAi(userMessage: String) {
        if (userMessage.isBlank()) return
        val current = _chatMessages.value.toMutableList()
        current.add(ChatMessage("user", userMessage))
        _chatMessages.value = current

        _isAiLoading.value = true
        viewModelScope.launch {
            val history = current.takeLast(6).map { it.sender to it.text }
            val reply = aiTutorRepository.askTutor(userMessage, history)
            _isAiLoading.value = false
            val updated = _chatMessages.value.toMutableList()
            updated.add(ChatMessage("ai", reply))
            _chatMessages.value = updated
        }
    }

    fun submitMpesaPayment(plan: SubscriptionPlan, mpesaRef: String, phone: String, imageUri: String?) {
        viewModelScope.launch {
            val receiptId = paymentRepository.submitPaymentReceipt(
                planRequested = plan.name,
                amountTzs = plan.priceTzs,
                mpesaRef = mpesaRef,
                phoneNumber = phone,
                receiptImageUri = imageUri
            )
            _paymentSubmissionSuccess.value = "Risiti #${receiptId} imewasilishwa kwa mafanikio! Inasubiri uhakiki wa Msimamizi."
        }
    }

    fun resetPaymentSubmissionNotice() {
        _paymentSubmissionSuccess.value = null
    }

    fun verifyPaymentAsAdmin(receiptId: Long, approve: Boolean, notes: String) {
        viewModelScope.launch {
            paymentRepository.verifyReceiptByAdmin(receiptId, approve, notes)
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsHelper.shutdown()
    }
}
