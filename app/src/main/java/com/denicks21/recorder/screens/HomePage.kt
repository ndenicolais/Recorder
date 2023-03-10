package com.denicks21.recorder.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.denicks21.recorder.MainActivity
import com.denicks21.recorder.navigation.NavScreens.HomePage.title
import com.denicks21.recorder.ui.composables.CustomBackPress
import com.denicks21.recorder.ui.composables.CustomTopBar
import com.denicks21.recorder.ui.theme.GreyDark
import com.denicks21.recorder.ui.theme.YellowDark
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavHostController,
    openDrawer: () -> Unit,
) {
    CustomBackPress(
        onBackPressed = {}
    )

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val speechContext = context as MainActivity
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm")
    val current = LocalDateTime.now().format(formatter)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopBar(
                title,
                openDrawer
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "START NEW RECORD",
                    modifier = Modifier.padding(5.dp),
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    FloatingActionButton(
                        onClick = {
                            File(
                                speechContext.getExternalFilesDir("Records"),
                                "Recording_$current.mp3"
                            ).also {
                                speechContext.recorderPlayer.start(it)
                                speechContext.audioFile = it
                                Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show()
                            }
                        },
                        backgroundColor = YellowDark,
                        contentColor = GreyDark
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Start recording"
                        )
                    }
                    FloatingActionButton(
                        onClick = {
                            speechContext.recorderPlayer.stop()
                            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        },
                        backgroundColor = YellowDark,
                        contentColor = GreyDark
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Stop,
                            contentDescription = "Stop recording"
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.height(50.dp)
                )
                Divider(
                    thickness = 0.5.dp,
                    color = Color.Black,
                    modifier = Modifier.width(300.dp)
                )
                Spacer(
                    modifier = Modifier.height(50.dp)
                )
                Text(
                    text = "LISTEN LAST RECORD",
                    modifier = Modifier.padding(5.dp),
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    FloatingActionButton(
                        onClick = {
                            speechContext.player.playFile(
                                speechContext.audioFile ?: return@FloatingActionButton
                            )
                            Toast.makeText(context, "Played", Toast.LENGTH_SHORT).show()
                        },
                        backgroundColor = GreyDark,
                        contentColor = YellowDark
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play"
                        )
                    }
                    FloatingActionButton(
                        onClick = {
                            speechContext.player.stop()
                            Toast.makeText(context, "Stopped", Toast.LENGTH_SHORT).show()
                        },
                        backgroundColor = GreyDark,
                        contentColor = YellowDark
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Stop,
                            contentDescription = "Stop playing"
                        )
                    }
                }
            }
        }
    }
}