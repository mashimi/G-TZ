package com.example.data.remote

import android.content.Context
import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

data class FirestoreReceipt(
    val id: String = "",
    val userId: Int = 1,
    val userName: String = "",
    val planRequested: String = "",
    val amountTzs: Int = 0,
    val mpesaRef: String = "",
    val phoneNumber: String = "",
    val receiptImageUrl: String? = null,
    val status: String = "PENDING",
    val submittedAtTimestamp: Long = System.currentTimeMillis(),
    val verifiedAtTimestamp: Long? = null,
    val adminNotes: String? = null
)

class FirebasePaymentService {

    private fun getFirestore(): FirebaseFirestore? {
        return try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun uploadReceiptImage(context: Context, imageUri: Uri): String? {
        return imageUri.toString()
    }

    suspend fun submitPayment(receipt: FirestoreReceipt): String {
        return try {
            val firestore = getFirestore() ?: return "local_${System.currentTimeMillis()}"
            val docRef = firestore.collection("payment_receipts").document()
            val receiptWithId = receipt.copy(id = docRef.id)
            docRef.set(receiptWithId).await()
            docRef.id
        } catch (e: Exception) {
            e.printStackTrace()
            "local_${System.currentTimeMillis()}"
        }
    }

    fun getUserReceiptsFlow(userId: Int): Flow<List<FirestoreReceipt>> = callbackFlow {
        val firestore = getFirestore()
        if (firestore == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        try {
            val listener = firestore.collection("payment_receipts")
                .whereEqualTo("userId", userId)
                .orderBy("submittedAtTimestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(emptyList())
                        return@addSnapshotListener
                    }
                    val receipts = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(FirestoreReceipt::class.java)
                    } ?: emptyList()
                    trySend(receipts)
                }
            awaitClose { listener.remove() }
        } catch (e: Exception) {
            trySend(emptyList())
            close()
        }
    }

    fun getAllReceiptsFlow(): Flow<List<FirestoreReceipt>> = callbackFlow {
        val firestore = getFirestore()
        if (firestore == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        try {
            val listener = firestore.collection("payment_receipts")
                .orderBy("submittedAtTimestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(emptyList())
                        return@addSnapshotListener
                    }
                    val receipts = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(FirestoreReceipt::class.java)
                    } ?: emptyList()
                    trySend(receipts)
                }
            awaitClose { listener.remove() }
        } catch (e: Exception) {
            trySend(emptyList())
            close()
        }
    }

    suspend fun verifyReceipt(receiptId: String, approve: Boolean, adminNotes: String) {
        val firestore = getFirestore() ?: return
        try {
            val status = if (approve) "APPROVED" else "REJECTED"
            firestore.collection("payment_receipts").document(receiptId).update(
                mapOf(
                    "status" to status,
                    "verifiedAtTimestamp" to System.currentTimeMillis(),
                    "adminNotes" to adminNotes
                )
            ).await()

            if (approve) {
                val receiptDoc = firestore.collection("payment_receipts").document(receiptId).get().await()
                val receipt = receiptDoc.toObject(FirestoreReceipt::class.java)
                if (receipt != null) {
                    val thirtyDays = 30L * 24L * 60L * 60L * 1000L
                    firestore.collection("users")
                        .document(receipt.userId.toString())
                        .update(
                            mapOf(
                                "currentPlan" to receipt.planRequested,
                                "planExpiryTimestamp" to (System.currentTimeMillis() + thirtyDays)
                            )
                        ).await()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
