package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PrepopulatedData
import com.example.ui.theme.AmberGold
import com.example.ui.theme.GermanCrimson
import com.example.ui.theme.MpesaGreen
import com.example.ui.theme.PrimaryNavy

@Composable
fun QuizScreen(
    lessonId: Int,
    onBack: () -> Unit,
    onQuizCompleted: (xpEarned: Int) -> Unit
) {
    val questions = PrepopulatedData.getQuizForLesson(lessonId)
    if (questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Hakuna maswali yaliyopatikana kwa somo hili.")
        }
        return
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableIntStateOf(0) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var isQuizFinished by remember { mutableStateOf(false) }

    val currentQuestion = questions[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("screen_quiz")
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("btn_back_quiz")
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Rudi")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Mazoezi ya Somo $lessonId",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PrimaryNavy
                )
                Text(
                    text = "Swali ${currentIndex + 1} kati ya ${questions.size}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Progress Bar
        LinearProgressIndicator(
            progress = { (currentIndex + 1).toFloat() / questions.size.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = MpesaGreen,
            trackColor = Color.LightGray.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (!isQuizFinished) {
            // Question Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = currentQuestion.questionSwahili,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        color = PrimaryNavy
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Options List
                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedOption == optIndex
                        val isCorrect = optIndex == currentQuestion.correctAnswerIndex

                        val containerColor = when {
                            isAnswerSubmitted && isCorrect -> MpesaGreen.copy(alpha = 0.2f)
                            isAnswerSubmitted && isSelected && !isCorrect -> GermanCrimson.copy(alpha = 0.2f)
                            isSelected -> PrimaryNavy.copy(alpha = 0.12f)
                            else -> Color.LightGray.copy(alpha = 0.15f)
                        }

                        val borderColor = when {
                            isAnswerSubmitted && isCorrect -> MpesaGreen
                            isAnswerSubmitted && isSelected && !isCorrect -> GermanCrimson
                            isSelected -> PrimaryNavy
                            else -> Color.Transparent
                        }

                        Surface(
                            onClick = {
                                if (!isAnswerSubmitted) {
                                    selectedOption = optIndex
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .testTag("quiz_option_$optIndex"),
                            shape = RoundedCornerShape(12.dp),
                            color = containerColor,
                            border = androidx.compose.foundation.BorderStroke(2.dp, borderColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${('A' + optIndex)}.",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = PrimaryNavy
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = optionText,
                                    modifier = Modifier.weight(1f),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                if (isAnswerSubmitted) {
                                    if (isCorrect) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Sahihi",
                                            tint = MpesaGreen
                                        )
                                    } else if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Makosa",
                                            tint = GermanCrimson
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Explanation when submitted
                    if (isAnswerSubmitted) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = PrimaryNavy.copy(alpha = 0.08f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "💡 Maelezo ya Kiswahili:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = PrimaryNavy
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = currentQuestion.explanationSwahili,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action Button (Thibitisha / Inayofuata)
            if (!isAnswerSubmitted) {
                Button(
                    onClick = {
                        if (selectedOption != null) {
                            isAnswerSubmitted = true
                            if (selectedOption == currentQuestion.correctAnswerIndex) {
                                score++
                            }
                        }
                    },
                    enabled = selectedOption != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_submit_answer"),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Thibitisha Jibu", fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = {
                        if (currentIndex + 1 < questions.size) {
                            currentIndex++
                            selectedOption = null
                            isAnswerSubmitted = false
                        } else {
                            isQuizFinished = true
                            onQuizCompleted(50) // Award 50 XP
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_next_question"),
                    colors = ButtonDefaults.buttonColors(containerColor = MpesaGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        if (currentIndex + 1 < questions.size) "Swali Linalofuata ➔" else "Maliza Mazoezi 🎉",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            // Finished Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Hongera",
                        tint = AmberGold,
                        modifier = Modifier.size(72.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Hongera Sana! 🎉",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = PrimaryNavy
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Umekamilisha Mazoezi ya Somo $lessonId!",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Matokeo Yako: $score / ${questions.size}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = MpesaGreen
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = AmberGold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "+50 XP Zimeongezwa kwenye Akaunti Yako!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = PrimaryNavy
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onBack,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("btn_finish_quiz"),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Rudi kwenye Orodha ya Masomo", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
