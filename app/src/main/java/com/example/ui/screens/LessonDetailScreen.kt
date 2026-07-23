package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PrepopulatedData
import com.example.data.model.LessonEntity
import com.example.ui.components.AudioSpeakerButton
import com.example.ui.components.LevelBadge
import com.example.ui.theme.AmberGold
import com.example.ui.theme.MpesaGreen
import com.example.ui.theme.PrimaryNavy

@Composable
fun LessonDetailScreen(
    lesson: LessonEntity?,
    onBack: () -> Unit,
    onStartQuiz: (Int) -> Unit,
    onSpeakGerman: (String) -> Unit
) {
    if (lesson == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Somo halikupatikana.")
        }
        return
    }

    val vocabList = PrepopulatedData.getVocabForLesson(lesson.id)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("screen_lesson_detail")
    ) {
        // Top Action Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("btn_back_lesson")
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Rudi")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Somo ${lesson.lessonNumber}: ${lesson.titleSwahili}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PrimaryNavy
                )
                Text(
                    text = lesson.titleGerman,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            LevelBadge(level = lesson.level)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lesson Swahili Intro
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryNavy.copy(alpha = 0.06f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Intro",
                        tint = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Maelezo ya Mwanzo (Introduction)",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = lesson.swahiliIntro,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Vocabulary List with Audio Speaker TTS Buttons
        Text(
            text = "📖 Msamiati wa Kijerumani & Kiswahili",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = PrimaryNavy
        )
        Text(
            text = "Bonyeza alama ya spika kusikiliza matamshi sahihi ya Kijerumani!",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(10.dp))

        vocabList.forEach { vocab ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AudioSpeakerButton(
                        textToSpeak = vocab.german,
                        onSpeak = onSpeakGerman
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = vocab.german,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PrimaryNavy
                            )
                            if (vocab.pronunciationNote.isNotBlank()) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "(${vocab.pronunciationNote})",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Text(
                            text = vocab.swahili,
                            fontSize = 13.sp,
                            color = MpesaGreen,
                            fontWeight = FontWeight.Medium
                        )

                        if (vocab.exampleSentenceGerman.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Mfano: ${vocab.exampleSentenceGerman}",
                                fontSize = 11.sp,
                                color = Color.DarkGray
                            )
                            Text(
                                text = "→ ${vocab.exampleSentenceSwahili}",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Grammar Rules Explanation in Swahili
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = AmberGold.copy(alpha = 0.12f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = "Grammar",
                        tint = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "📐 Kanuni za Sarufi (Grammar Rules)",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lesson.grammarExplanationSwahili,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cultural Tip (Tanzania context)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MpesaGreen.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Cultural Tip",
                        tint = MpesaGreen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "💡 Dokezo la Utamaduni wa Ujerumani & Tanzania",
                        fontWeight = FontWeight.Bold,
                        color = MpesaGreen,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = lesson.culturalTipSwahili,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Start Quiz Action
        Button(
            onClick = { onStartQuiz(lesson.id) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("btn_start_quiz"),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Fanya Mazoezi & Maswali (Quiz)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
