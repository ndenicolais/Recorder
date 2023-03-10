package com.denicks21.recorder.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavScreens(val title: String, val route: String, var icon: ImageVector) {
    object IntroPage : NavScreens(
        "Intro", "IntroPage", Icons.Default.Android
    )

    object HomePage : NavScreens(
        "Home", "HomePage", Icons.Default.Home
    )

    object RecorderPlayerPage: NavScreens(
        "Recorder", "RecorderPlayerPage", Icons.Default.KeyboardVoice
    )

    object AudioPlayerPage: NavScreens(
        "Audio player", "AudioPlayerPage", Icons.Default.VolumeUp
    )

    object InfoPage: NavScreens(
        "Info", "InfoPage", Icons.Default.Info
    )
}