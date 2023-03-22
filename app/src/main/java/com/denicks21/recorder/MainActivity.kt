package com.denicks21.recorder

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.ActivityCompat
import com.denicks21.recorder.navigation.NavGraph
import com.denicks21.recorder.play.AudioPlayer
import com.denicks21.recorder.record.RecorderPlayer
import com.denicks21.recorder.ui.composables.NavigationDrawer
import com.denicks21.recorder.ui.theme.GreyDark
import com.denicks21.recorder.ui.theme.RecorderTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : ComponentActivity() {
    val recorderPlayer by lazy { RecorderPlayer(applicationContext) }
    val player by lazy { AudioPlayer(applicationContext) }
    var audioFile: File? = null

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )
        setContent {
            RecorderTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setNavigationBarColor(
                        GreyDark,
                        darkIcons = false
                    )
                }
                val navController = rememberAnimatedNavController()

                Surface {
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                    ModalDrawer(
                        drawerState = drawerState,
                        gesturesEnabled = drawerState.isOpen,
                        drawerContent = {
                            NavigationDrawer(
                                onDestinationClicked = { route ->
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(route) {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            popUpTo(route) {
                                                saveState = true
                                            }
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    ) {
                        NavGraph(
                            navController,
                            openDrawer = {
                                openDrawer()
                            }
                        )
                    }
                }
            }
        }
    }
}