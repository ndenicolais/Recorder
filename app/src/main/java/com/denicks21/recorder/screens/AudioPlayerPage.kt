package com.denicks21.recorder.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.denicks21.recorder.MainActivity
import com.denicks21.recorder.ui.theme.Confirm
import com.denicks21.recorder.ui.theme.DarkGrey
import com.denicks21.recorder.ui.theme.DarkText
import com.denicks21.recorder.ui.theme.LightText
import com.denicks21.recorder.ui.theme.LightYellow
import com.denicks21.recorder.ui.theme.Refuse

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AudioPlayerPage(navController: NavHostController) {
    val context = LocalContext.current
    val speechContext = context as MainActivity

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Records List",
                        color = if (isSystemInDarkTheme()) DarkText else LightText
                    )
                },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(speechContext.audioFileList) { audioFile ->
                    val fileName by remember { mutableStateOf(audioFile.name) }
                    var newFileName by remember { mutableStateOf("") }
                    var isDeleteDialogOpen by remember { mutableStateOf(false) }
                    var isRenameDialogOpen by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .width(320.dp)
                            .height(120.dp),
                        shape = RoundedCornerShape(25.dp),
                        backgroundColor = if (isSystemInDarkTheme()) LightYellow else DarkGrey,
                        elevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = fileName,
                                color = if (isSystemInDarkTheme()) DarkGrey else LightYellow
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { speechContext.player.playFile(audioFile) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.PlayArrow,
                                        contentDescription = "Play record",
                                        tint = if (isSystemInDarkTheme()) DarkGrey else LightYellow
                                    )
                                }
                                IconButton(
                                    onClick = { speechContext.player.stop() }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Stop,
                                        contentDescription = "Stop playing record",
                                        tint = if (isSystemInDarkTheme()) DarkGrey else LightYellow
                                    )
                                }
                                IconButton(
                                    onClick = { isRenameDialogOpen = true }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "Rename record",
                                        tint = if (isSystemInDarkTheme()) DarkGrey else LightYellow
                                    )
                                }
                                IconButton(
                                    onClick = { isDeleteDialogOpen = true }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Delete record",
                                        tint = Refuse
                                    )
                                }
                            }
                        }
                        if (isDeleteDialogOpen) {
                            AlertDialog(
                                onDismissRequest = { isDeleteDialogOpen = false },
                                title = {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "DELETE RECORD",
                                            modifier = Modifier.fillMaxWidth(),
                                            color = if (isSystemInDarkTheme()) DarkGrey else LightYellow,
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                        Divider(
                                            color = if (isSystemInDarkTheme()) DarkGrey else LightYellow,
                                            thickness = 1.dp
                                        )
                                    }
                                },
                                text = {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Are you sure to delete this record?",
                                            color = if (isSystemInDarkTheme()) DarkGrey else LightYellow,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                },
                                dismissButton = {
                                    Button(
                                        onClick = { isDeleteDialogOpen = false },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Refuse
                                        )
                                    ) {
                                        Text(
                                            text = "No",
                                            color = Color.White
                                        )
                                    }
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            speechContext.removeAudioFile(audioFile)
                                            isDeleteDialogOpen = false
                                            navController.popBackStack()
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Confirm
                                        )
                                    ) {
                                        Text(
                                            text = "Yes",
                                            color = Color.White
                                        )
                                    }
                                },
                                backgroundColor = if (isSystemInDarkTheme()) LightYellow else DarkGrey
                            )
                        }
                        if (isRenameDialogOpen) {
                            Dialog(
                                onDismissRequest = { isRenameDialogOpen = false }
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .height(250.dp)
                                        .width(300.dp),
                                    shape = RoundedCornerShape(size = 8.dp),
                                    color = if (isSystemInDarkTheme()) DarkGrey else LightYellow
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(20.dp)
                                    ) {
                                        Text(
                                            text = "RENAME RECORD",
                                            modifier = Modifier.fillMaxWidth(),
                                            color = if (isSystemInDarkTheme()) LightYellow else DarkGrey,
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                        Divider(
                                            color = if (isSystemInDarkTheme()) LightYellow else DarkGrey,
                                            thickness = 1.dp
                                        )
                                        Column(
                                            modifier = Modifier.padding(10.dp),
                                            verticalArrangement = Arrangement.spacedBy(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            OutlinedTextField(
                                                value = newFileName,
                                                onValueChange = { newFileName = it },
                                                label = { Text("New File Name") },
                                                colors = TextFieldDefaults.textFieldColors(
                                                    textColor = if (isSystemInDarkTheme()) LightYellow else DarkGrey,
                                                    backgroundColor = if (isSystemInDarkTheme()) DarkGrey else LightYellow,
                                                ),
                                                keyboardOptions = KeyboardOptions.Default.copy(
                                                    keyboardType = KeyboardType.Text
                                                ),
                                                singleLine = true,
                                                maxLines = 1
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxSize(),
                                                verticalAlignment = Alignment.Bottom,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Button(
                                                    onClick = {
                                                        if (newFileName.isNotEmpty()) {
                                                            speechContext.renameAudioFile(
                                                                fileName,
                                                                "$newFileName.mp3"
                                                            )
                                                            isRenameDialogOpen = false
                                                            newFileName = ""
                                                        }
                                                    },
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Confirm
                                                    )
                                                ) {
                                                    Text(
                                                        text = "Rename",
                                                        color = Color.White
                                                    )
                                                }
                                                Button(
                                                    onClick = {
                                                        isRenameDialogOpen = false
                                                        newFileName = ""
                                                    },
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Refuse
                                                    )
                                                ) {
                                                    Text(
                                                        text = "Cancel",
                                                        color = Color.White
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}