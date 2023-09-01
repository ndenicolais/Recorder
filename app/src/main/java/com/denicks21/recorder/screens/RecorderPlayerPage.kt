package com.denicks21.recorder.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.denicks21.recorder.MainActivity
import com.denicks21.recorder.R
import com.denicks21.recorder.ui.theme.DarkGrey
import com.denicks21.recorder.ui.theme.DarkText
import com.denicks21.recorder.ui.theme.LightText
import com.denicks21.recorder.ui.theme.LightYellow
import java.io.File
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecorderPlayerPage(navController: NavHostController) {
    val context = LocalContext.current
    val speechContext = context as MainActivity
    var timer: Timer? by remember { mutableStateOf(null) }
    var recordingTime by remember { mutableStateOf(0L) }
    var lockedRecordingTime by remember { mutableStateOf(0L) }
    var isRecording by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Top) {
            TopAppBar(
                title = {
                    Text(
                        text = "Recorder Player",
                        color = if (isSystemInDarkTheme()) DarkText else LightText
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isSystemInDarkTheme()) DarkText else LightText
                        )
                    }
                },
                backgroundColor = if (isSystemInDarkTheme()) LightYellow else DarkGrey
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                shape = RoundedCornerShape(25.dp),
                backgroundColor = if (isSystemInDarkTheme()) LightYellow else DarkGrey,
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "NEW RECORD",
                        color = if (isSystemInDarkTheme()) DarkGrey else LightYellow,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(R.drawable.equalizer),
                        contentDescription = "Equalizer image",
                        colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) DarkGrey else LightYellow)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = formatTime(recordingTime),
                        color = if (isSystemInDarkTheme()) DarkText else LightText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FloatingActionButton(
                            onClick = {
                                val timestamp = System.currentTimeMillis()
                                val fileName = "Record_${timestamp}.mp3"
                                val newAudioFile = File(
                                    speechContext.getExternalFilesDir("Records"),
                                    fileName
                                )
                                if (!isRecording) {
                                    speechContext.recorderPlayer.start(newAudioFile)
                                    speechContext.audioFileList.add(newAudioFile)
                                    speechContext.saveFilePath(newAudioFile.absolutePath)
                                    if (timer == null) {
                                        timer = Timer()
                                        timer?.scheduleAtFixedRate(0L, 1000L) {
                                            recordingTime += 1000
                                        }
                                    }
                                    isRecording = true
                                    isPaused = false
                                } else if (isPaused) {
                                    speechContext.recorderPlayer.resume()
                                    timer = Timer()
                                    timer?.scheduleAtFixedRate(0L, 1000L) {
                                        recordingTime += 1000
                                    }
                                    isPaused = false
                                } else {
                                    speechContext.recorderPlayer.pause()
                                    timer?.cancel()
                                    isPaused = true
                                }
                            },
                            backgroundColor = if (isSystemInDarkTheme()) DarkGrey else LightYellow
                        ) {
                            Icon(
                                imageVector =
                                if (isRecording && !isPaused)
                                    Icons.Filled.Pause
                                else
                                    Icons.Filled.PlayArrow,
                                contentDescription = "Start recording",
                                tint = if (isSystemInDarkTheme()) LightYellow else DarkGrey
                            )
                        }
                        FloatingActionButton(
                            onClick = {
                                if (timer != null) {
                                    timer?.cancel()
                                    timer = null
                                }
                                speechContext.recorderPlayer.stop()
                                lockedRecordingTime = recordingTime
                                recordingTime = 0L
                                isRecording = false
                                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                            },
                            backgroundColor = if (isSystemInDarkTheme()) DarkGrey else LightYellow
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Stop,
                                contentDescription = "Stop recording",
                                tint = if (isSystemInDarkTheme()) LightYellow else DarkGrey
                            )
                        }
                    }
                }
            }
        }
    }
}

fun formatTime(milliseconds: Long): String {
    val seconds = milliseconds / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}