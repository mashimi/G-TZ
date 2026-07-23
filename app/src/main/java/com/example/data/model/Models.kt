package com.example.data.model

data class VocabItem(
    val german: String,
    val swahili: String,
    val pronunciationNote: String = "",
    val exampleSentenceGerman: String = "",
    val exampleSentenceSwahili: String = ""
)

data class QuizQuestion(
    val questionSwahili: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanationSwahili: String
)
