package com.denicks21.recorder

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.denicks21.recorder.navigation.NavGraph
import com.denicks21.recorder.play.AudioPlayer
import com.denicks21.recorder.record.RecorderPlayer
import com.denicks21.recorder.ui.theme.RecorderTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.io.File

class MainActivity : ComponentActivity() {
    val recorderPlayer by lazy { RecorderPlayer(applicationContext) }
    val player by lazy { AudioPlayer(applicationContext) }
    var audioFileList : MutableList<File> = mutableListOf()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )

        val savedPaths = loadSavedFilePaths()
        audioFileList.addAll(savedPaths.map { File(it) })

        setContent {
            RecorderTheme {
                val navController = rememberAnimatedNavController()
                val systemUiController = rememberSystemUiController()
                val statusBarColor = MaterialTheme.colors.surface
                val navigationBarColor = MaterialTheme.colors.onSurface
                val barIcons = isSystemInDarkTheme()

                SideEffect {
                    systemUiController.setNavigationBarColor(
                        color = navigationBarColor,
                        darkIcons = barIcons
                    )
                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                        darkIcons = true
                    )
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.surface
                ) {
                    NavGraph(navController)
                }
            }
        }
    }

    private fun loadSavedFilePaths(): List<String> {
        val file = File(filesDir, "saved_audio_paths.txt")
        return if (file.exists()) {
            file.readLines()
        } else {
            emptyList()
        }
    }

    fun saveFilePath(path: String) {
        val file = File(filesDir, "saved_audio_paths.txt")
        file.appendText("$path\n")
    }

    private fun saveFilePathsToFile() {
        val file = File(filesDir, "saved_audio_paths.txt")
        file.writeText(audioFileList.joinToString("\n"))
    }

    fun removeAudioFile(file: File) {
        if (file.exists()) {
            if (file.delete()) {
                audioFileList.remove(file)
                saveFilePathsToFile()
            }
        }
    }

    fun renameAudioFile(oldFileName: String, newFileName: String) {
        val oldFile = audioFileList.find { it.name == oldFileName }
        if (oldFile != null) {
            val newFile = File(oldFile.parent, newFileName)
            if (oldFile.renameTo(newFile)) {
                audioFileList.remove(oldFile)
                audioFileList.add(newFile)
                saveFilePathsToFile()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Salva i percorsi dei file audio prima di chiudere l'app
        saveFilePathsToFile()
    }
}