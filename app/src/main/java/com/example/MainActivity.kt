package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.components.AppBottomNavigation
import com.example.ui.components.Screen
import com.example.ui.screens.AdminVerificationScreen
import com.example.ui.screens.AiTutorScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.LessonCatalogScreen
import com.example.ui.screens.LessonDetailScreen
import com.example.ui.screens.PaymentScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen(mainViewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Dashboard.route

    val user by mainViewModel.userState.collectAsStateWithLifecycle()
    val lessons by mainViewModel.allLessonsState.collectAsStateWithLifecycle()
    val receipts by mainViewModel.allReceiptsState.collectAsStateWithLifecycle()
    val chatMessages by mainViewModel.chatMessages.collectAsStateWithLifecycle()
    val isAiLoading by mainViewModel.isAiLoading.collectAsStateWithLifecycle()
    val selectedLevel by mainViewModel.selectedLevel.collectAsStateWithLifecycle()
    val paymentNotice by mainViewModel.paymentSubmissionSuccess.collectAsStateWithLifecycle()

    val showBottomBar = currentRoute in listOf(
        Screen.Dashboard.route,
        Screen.Catalog.route,
        Screen.AiTutor.route,
        Screen.Payment.route,
        Screen.Profile.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavigation(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Dashboard.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    user = user,
                    lessons = lessons,
                    onOpenLesson = { lessonId ->
                        navController.navigate(Screen.LessonDetail.createRoute(lessonId))
                    },
                    onOpenCatalog = {
                        navController.navigate(Screen.Catalog.route)
                    },
                    onOpenAiTutor = {
                        navController.navigate(Screen.AiTutor.route)
                    },
                    onOpenPayment = {
                        navController.navigate(Screen.Payment.route)
                    }
                )
            }

            composable(Screen.Catalog.route) {
                LessonCatalogScreen(
                    user = user,
                    lessons = lessons,
                    selectedLevel = selectedLevel,
                    onSelectLevel = { mainViewModel.setSelectedLevel(it) },
                    onOpenLesson = { lessonId ->
                        navController.navigate(Screen.LessonDetail.createRoute(lessonId))
                    },
                    onOpenPayment = {
                        navController.navigate(Screen.Payment.route)
                    }
                )
            }

            composable(
                route = Screen.LessonDetail.route,
                arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
            ) { backStack ->
                val lessonId = backStack.arguments?.getInt("lessonId") ?: 1
                val lesson = lessons.find { it.id == lessonId }
                LessonDetailScreen(
                    lesson = lesson,
                    onBack = { navController.popBackStack() },
                    onStartQuiz = { id ->
                        navController.navigate(Screen.Quiz.createRoute(id))
                    },
                    onSpeakGerman = { text ->
                        mainViewModel.speakGerman(text)
                    }
                )
            }

            composable(
                route = Screen.Quiz.route,
                arguments = listOf(navArgument("lessonId") { type = NavType.IntType })
            ) { backStack ->
                val lessonId = backStack.arguments?.getInt("lessonId") ?: 1
                QuizScreen(
                    lessonId = lessonId,
                    onBack = { navController.popBackStack() },
                    onQuizCompleted = { xp ->
                        mainViewModel.completeLessonAndAwardXp(lessonId, xp)
                    }
                )
            }

            composable(Screen.AiTutor.route) {
                AiTutorScreen(
                    messages = chatMessages,
                    isLoading = isAiLoading,
                    onSendMessage = { text ->
                        mainViewModel.sendMessageToAi(text)
                    }
                )
            }

            composable(Screen.Payment.route) {
                PaymentScreen(
                    user = user,
                    receipts = receipts,
                    submissionNotice = paymentNotice,
                    onSubmitPayment = { plan, ref, phone, img ->
                        mainViewModel.submitMpesaPayment(plan, ref, phone, img)
                    },
                    onResetNotice = { mainViewModel.resetPaymentSubmissionNotice() },
                    onOpenAdminPanel = { navController.navigate(Screen.Admin.route) }
                )
            }

            composable(Screen.Admin.route) {
                AdminVerificationScreen(
                    receipts = receipts,
                    onBack = { navController.popBackStack() },
                    onVerifyReceipt = { receiptId, approve, notes ->
                        mainViewModel.verifyPaymentAsAdmin(receiptId, approve, notes)
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    user = user,
                    onOpenPayment = { navController.navigate(Screen.Payment.route) },
                    onOpenAdminPanel = { navController.navigate(Screen.Admin.route) }
                )
            }
        }
    }
}
