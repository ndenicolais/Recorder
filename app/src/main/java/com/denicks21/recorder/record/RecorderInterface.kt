package com.denicks21.recorder.record

import java.io.File

interface RecorderInterface {
    fun start(outputFile: File)
    fun stop()
}