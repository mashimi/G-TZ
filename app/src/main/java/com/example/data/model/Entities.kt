package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class SubscriptionPlan(val title: String, val priceTzs: Int, val maxLevelUnlocked: String) {
    FREE("A2 - Mwanzo Libre", 0, "A1"),
    BASIC("Basic Plan", 5000, "A2"),
    STANDARD("Standard Plan", 10000, "B1"),
    PREMIUM("Premium Plan", 15000, "B2")
}

enum class PaymentStatus {
    PENDING,
    APPROVED,
    REJECTED
}

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Mwanafunzi wa Kijerumani",
    val email: String = "mwanafunzi@tz.co.tz",
    val phone: String = "+255712345678",
    val currentPlan: String = SubscriptionPlan.FREE.name,
    val planExpiryTimestamp: Long = 0L,
    val totalXp: Int = 120,
    val streakDays: Int = 3,
    val completedLessonsCount: Int = 2
)

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val level: String, // A1, A2, B1, B2
    val lessonNumber: Int,
    val titleGerman: String,
    val titleSwahili: String,
    val swahiliIntro: String,
    val grammarExplanationSwahili: String,
    val culturalTipSwahili: String,
    val isCompleted: Boolean = false,
    val isLocked: Boolean = false,
    val requiredPlan: String = "FREE" // FREE, BASIC, STANDARD, PREMIUM
)

@Entity(tableName = "payment_receipts")
data class PaymentReceiptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Int = 1,
    val planRequested: String,
    val amountTzs: Int,
    val mpesaRef: String,
    val phoneNumber: String,
    val receiptImageUri: String? = null,
    val status: String = PaymentStatus.PENDING.name,
    val submittedAtTimestamp: Long = System.currentTimeMillis(),
    val verifiedAtTimestamp: Long? = null,
    val adminNotes: String? = null
)
