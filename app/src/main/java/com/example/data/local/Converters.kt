package com.example.data.local

import androidx.room.TypeConverter
import com.example.data.model.QuizQuestion
import com.example.data.model.VocabItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val vocabListType = Types.newParameterizedType(List::class.java, VocabItem::class.java)
    private val vocabAdapter = moshi.adapter<List<VocabItem>>(vocabListType)

    private val quizListType = Types.newParameterizedType(List::class.java, QuizQuestion::class.java)
    private val quizAdapter = moshi.adapter<List<QuizQuestion>>(quizListType)

    @TypeConverter
    fun fromVocabList(value: List<VocabItem>?): String {
        return value?.let { vocabAdapter.toJson(it) } ?: "[]"
    }

    @TypeConverter
    fun toVocabList(value: String): List<VocabItem> {
        return try {
            vocabAdapter.fromJson(value) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromQuizList(value: List<QuizQuestion>?): String {
        return value?.let { quizAdapter.toJson(it) } ?: "[]"
    }

    @TypeConverter
    fun toQuizList(value: String): List<QuizQuestion> {
        return try {
            quizAdapter.fromJson(value) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
