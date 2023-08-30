package com.denicks21.recorder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.denicks21.recorder.MainActivity
import com.denicks21.recorder.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavScreens.IntroPage.route
    )
    {
        composable(route = NavScreens.IntroPage.route) {
            IntroPage(navController)
        }
        composable(route = NavScreens.HomePage.route) {
            HomePage(navController)
        }
        composable(route = NavScreens.RecorderPlayerPage.route) {
            RecorderPlayerPage(navController)
        }
        composable(route = NavScreens.AudioPlayerPage.route) {
            AudioPlayerPage(navController)
        }
    }
}