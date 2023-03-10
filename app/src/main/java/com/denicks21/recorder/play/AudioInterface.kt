package com.denicks21.recorder.play

import java.io.File

interface AudioInterface {
    fun playFile(file: File)
    fun stop()
}