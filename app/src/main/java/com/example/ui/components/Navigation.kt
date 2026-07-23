package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.example.ui.theme.MpesaGreen
import com.example.ui.theme.PrimaryNavy

sealed class Screen(val route: String, val titleSwahili: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Mwanzo", Icons.Default.Home)
    object Catalog : Screen("catalog", "Masomo", Icons.Default.School)
    object AiTutor : Screen("ai_tutor", "Mwalimu AI", Icons.Default.AutoAwesome)
    object Payment : Screen("payment", "Malipo M-Pesa", Icons.Default.Payment)
    object Profile : Screen("profile", "Akaunti", Icons.Default.Person)
    object Admin : Screen("admin", "Admin Panel", Icons.Default.Person)
    object LessonDetail : Screen("lesson_detail/{lessonId}", "Somo Detail", Icons.Default.School) {
        fun createRoute(lessonId: Int) = "lesson_detail/$lessonId"
    }
    object Quiz : Screen("quiz/{lessonId}", "Maswali & Mazoezi", Icons.Default.School) {
        fun createRoute(lessonId: Int) = "quiz/$lessonId"
    }
}

@Composable
fun AppBottomNavigation(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        Screen.Dashboard,
        Screen.Catalog,
        Screen.AiTutor,
        Screen.Payment,
        Screen.Profile
    )

    NavigationBar(
        containerColor = androidx.compose.ui.graphics.Color(0xFFF3EDF7),
        contentColor = androidx.compose.ui.graphics.Color(0xFF1D1B20)
    ) {
        items.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                modifier = Modifier.testTag("nav_item_${screen.route}"),
                selected = selected,
                onClick = { onNavigate(screen.route) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.titleSwahili,
                        tint = if (selected) androidx.compose.ui.graphics.Color(0xFF1D192B) else androidx.compose.ui.graphics.Color(0xFF49454F)
                    )
                },
                label = {
                    Text(
                        text = screen.titleSwahili,
                        color = if (selected) androidx.compose.ui.graphics.Color(0xFF1D192B) else androidx.compose.ui.graphics.Color(0xFF49454F)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = androidx.compose.ui.graphics.Color(0xFFE8DEF8),
                    selectedIconColor = androidx.compose.ui.graphics.Color(0xFF1D192B),
                    selectedTextColor = androidx.compose.ui.graphics.Color(0xFF1D192B),
                    unselectedIconColor = androidx.compose.ui.graphics.Color(0xFF49454F),
                    unselectedTextColor = androidx.compose.ui.graphics.Color(0xFF49454F)
                )
            )
        }
    }
}
