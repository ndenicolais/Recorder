package com.denicks21.recorder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.denicks21.recorder.screens.*

@Composable
fun NavGraph(
    navController: NavHostController,
    openDrawer: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = NavScreens.IntroPage.route
    )
    {
        composable(route = NavScreens.IntroPage.route) {
            IntroPage(navController)
        }
        composable(route = NavScreens.HomePage.route) {
            HomePage(navController, openDrawer)
        }
        composable(route = NavScreens.RecorderPlayerPage.route) {
            RecorderPlayerPage(navController, openDrawer)
        }
        composable(route = NavScreens.AudioPlayerPage.route) {
            AudioPlayerPage(navController, openDrawer)
        }
        composable(route = NavScreens.InfoPage.route) {
            InfoPage(navController, openDrawer)
        }
    }
}