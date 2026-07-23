package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.LessonEntity
import com.example.data.model.PaymentReceiptEntity
import com.example.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = 1 LIMIT 1")
    fun getUserFlow(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = 1 LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserEntity)

    @Query("UPDATE users SET totalXp = totalXp + :xp, completedLessonsCount = completedLessonsCount + 1 WHERE id = 1")
    suspend fun addXpAndLesson(xp: Int)

    @Query("UPDATE users SET currentPlan = :plan, planExpiryTimestamp = :expiry WHERE id = 1")
    suspend fun updateSubscription(plan: String, expiry: Long)
}

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons ORDER BY level ASC, lessonNumber ASC")
    fun getAllLessonsFlow(): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE level = :level ORDER BY lessonNumber ASC")
    fun getLessonsByLevelFlow(level: String): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE id = :id LIMIT 1")
    suspend fun getLessonById(id: Int): LessonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Query("UPDATE lessons SET isCompleted = 1 WHERE id = :id")
    suspend fun markLessonCompleted(id: Int)

    @Query("SELECT COUNT(*) FROM lessons")
    suspend fun getLessonCount(): Int
}

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payment_receipts ORDER BY submittedAtTimestamp DESC")
    fun getAllReceiptsFlow(): Flow<List<PaymentReceiptEntity>>

    @Query("SELECT * FROM payment_receipts WHERE userId = :userId ORDER BY submittedAtTimestamp DESC")
    fun getUserReceiptsFlow(userId: Int): Flow<List<PaymentReceiptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: PaymentReceiptEntity): Long

    @Query("UPDATE payment_receipts SET status = :status, verifiedAtTimestamp = :verifiedAt, adminNotes = :notes WHERE id = :id")
    suspend fun updateReceiptStatus(id: Long, status: String, verifiedAt: Long, notes: String?)
}
