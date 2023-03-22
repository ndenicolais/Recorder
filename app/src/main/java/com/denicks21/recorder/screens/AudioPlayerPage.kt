package com.denicks21.recorder.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.denicks21.recorder.MainActivity
import com.denicks21.recorder.navigation.NavScreens.AudioPlayerPage.title
import com.denicks21.recorder.ui.composables.TopBar
import com.denicks21.recorder.ui.theme.GreyDark
import com.denicks21.recorder.ui.theme.YellowDark

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AudioPlayerPage(
    navController: NavHostController,
    openDrawer: () -> Unit,
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val speechContext = context as MainActivity

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title,
                openDrawer
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp),
                shape = RoundedCornerShape(100.dp),
                backgroundColor = YellowDark,
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "LISTEN LAST RECORD",
                        color = GreyDark,
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
}