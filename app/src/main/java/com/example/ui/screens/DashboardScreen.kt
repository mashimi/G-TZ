package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.LessonEntity
import com.example.data.model.UserEntity
import com.example.ui.components.LevelBadge
import com.example.ui.components.UserProgressHeaderCard
import com.example.ui.theme.AmberGold
import com.example.ui.theme.MpesaGreen
import com.example.ui.theme.PrimaryNavy

@Composable
fun DashboardScreen(
    user: UserEntity?,
    lessons: List<LessonEntity>,
    onOpenLesson: (Int) -> Unit,
    onOpenCatalog: () -> Unit,
    onOpenAiTutor: () -> Unit,
    onOpenPayment: () -> Unit
) {
    val scrollState = rememberScrollState()
    val nextLesson = lessons.firstOrNull { !it.isCompleted } ?: lessons.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("screen_dashboard")
    ) {
        // User Header Progress
        UserProgressHeaderCard(
            user = user,
            onOpenPayment = onOpenPayment
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hero Banner Art
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_german_swahili_banner_1784813241017),
                contentDescription = "Jifunze Kijerumani kwa Kiswahili",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Continue Learning Next Lesson Card
        if (nextLesson != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_next_lesson"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "📌 Somo Linalofuata",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MpesaGreen
                        )
                        LevelBadge(level = nextLesson.level, isLocked = nextLesson.isLocked)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Somo ${nextLesson.lessonNumber}: ${nextLesson.titleSwahili}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = PrimaryNavy
                    )
                    Text(
                        text = nextLesson.titleGerman,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { onOpenLesson(nextLesson.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_continue_lesson"),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Soma Somo Hili Sasa")
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Feature Shortcuts Grid
        Text(
            text = "🚀 Huduma za Haraka",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = PrimaryNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Shortcut 1: Mwalimu AI
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOpenAiTutor() }
                    .testTag("shortcut_ai_tutor"),
                shape = RoundedCornerShape(14.dp),
                color = PrimaryNavy.copy(alpha = 0.08f)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Mwalimu AI",
                        tint = PrimaryNavy,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Mwalimu AI",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = PrimaryNavy
                    )
                    Text(
                        text = "Uliza Maswali",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Shortcut 2: Lipa M-Pesa
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOpenPayment() }
                    .testTag("shortcut_mpesa"),
                shape = RoundedCornerShape(14.dp),
                color = MpesaGreen.copy(alpha = 0.12f)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Payment,
                        contentDescription = "Lipa M-Pesa",
                        tint = MpesaGreen,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Lipa M-Pesa",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MpesaGreen
                    )
                    Text(
                        text = "Fungua B1/B2",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Levels Overview Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📚 Viwango vya Masomo (A1 - B2)",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PrimaryNavy
            )
            Text(
                text = "Tazama Yote",
                fontSize = 12.sp,
                color = MpesaGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onOpenCatalog() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        val levelsList = listOf("A1", "A2", "B1", "B2")
        levelsList.forEach { level ->
            val levelLessons = lessons.filter { it.level == level }
            val completedCount = levelLessons.count { it.isCompleted }
            val totalCount = levelLessons.size.coerceAtLeast(1)
            val progress = completedCount.toFloat() / totalCount.toFloat()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onOpenCatalog() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LevelBadge(level = level)
                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = when(level) {
                                "A1" -> "A1: Masomo ya Mwanzo (Bure)"
                                "A2" -> "A2: Maisha ya Kila Siku"
                                "B1" -> "B1: Mazungumzo & Kazi"
                                else -> "B2: Mambo ya Kiofisi & Ufasaha"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape),
                            color = MpesaGreen,
                            trackColor = Color.LightGray.copy(alpha = 0.4f)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "$completedCount/$totalCount",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
