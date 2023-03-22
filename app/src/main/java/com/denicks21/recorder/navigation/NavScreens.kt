package com.denicks21.recorder.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavScreens(val title: String, val route: String, var icon: ImageVector) {
    object IntroPage : NavScreens(
        "Intro", "IntroPage", Icons.Filled.Android
    )

    object HomePage : NavScreens(
        "Home", "HomePage", Icons.Filled.Home
    )

    object RecorderPlayerPage: NavScreens(
        "Recorder", "RecorderPlayerPage", Icons.Filled.KeyboardVoice
    )

    object AudioPlayerPage: NavScreens(
        "Audio player", "AudioPlayerPage", Icons.Filled.VolumeUp
    )

    object InfoPage: NavScreens(
        "Info", "InfoPage", Icons.Filled.Info
    )
}