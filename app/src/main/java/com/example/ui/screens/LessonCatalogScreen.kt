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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LessonEntity
import com.example.data.model.UserEntity
import com.example.ui.components.LevelBadge
import com.example.ui.theme.GermanCrimson
import com.example.ui.theme.MpesaGreen
import com.example.ui.theme.PrimaryNavy

@Composable
fun LessonCatalogScreen(
    user: UserEntity?,
    lessons: List<LessonEntity>,
    selectedLevel: String,
    onSelectLevel: (String) -> Unit,
    onOpenLesson: (Int) -> Unit,
    onOpenPayment: () -> Unit
) {
    val levels = listOf("A1", "A2", "B1", "B2")
    val filteredLessons = lessons.filter { it.level == selectedLevel }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("screen_catalog")
    ) {
        Text(
            text = "📚 Orodha ya Masomo ya Kijerumani",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = PrimaryNavy
        )
        Text(
            text = "Chagua Kiwango chako cha masomo kuanzia A1 mpaka B2",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Level Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            levels.forEach { level ->
                val isSelected = selectedLevel == level
                Surface(
                    onClick = { onSelectLevel(level) },
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) PrimaryNavy else Color.LightGray.copy(alpha = 0.3f),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .testTag("tab_level_$level")
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = level,
                            color = if (isSelected) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lessons List
        if (filteredLessons.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hakuna masomo yaliyopatikana kwa sasa.",
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredLessons) { lesson ->
                    val userPlan = user?.currentPlan ?: "FREE"
                    // Check if plan allows this lesson
                    val isLockedByPlan = when (lesson.requiredPlan) {
                        "BASIC" -> userPlan == "FREE"
                        "STANDARD" -> userPlan == "FREE" || userPlan == "BASIC"
                        "PREMIUM" -> userPlan != "PREMIUM"
                        else -> false
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isLockedByPlan) {
                                    onOpenPayment()
                                } else {
                                    onOpenLesson(lesson.id)
                                }
                            }
                            .testTag("card_lesson_item_${lesson.id}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Icon state
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            lesson.isCompleted -> MpesaGreen.copy(alpha = 0.15f)
                                            isLockedByPlan -> GermanCrimson.copy(alpha = 0.15f)
                                            else -> PrimaryNavy.copy(alpha = 0.15f)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (lesson.isCompleted) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Imekamilika",
                                        tint = MpesaGreen
                                    )
                                } else if (isLockedByPlan) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Funga - Lipa M-Pesa",
                                        tint = GermanCrimson
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Anza",
                                        tint = PrimaryNavy
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Somo ${lesson.lessonNumber}: ${lesson.titleSwahili}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = PrimaryNavy
                                )
                                Text(
                                    text = lesson.titleGerman,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )

                                if (isLockedByPlan) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "🔒 Inahitaji ${lesson.requiredPlan} Plan (Lipa M-Pesa)",
                                        fontSize = 11.sp,
                                        color = GermanCrimson,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            LevelBadge(level = lesson.level)
                        }
                    }
                }
            }
        }
    }
}
