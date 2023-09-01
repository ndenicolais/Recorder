package com.denicks21.recorder.record

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.SystemClock
import java.io.File
import java.io.FileOutputStream

class RecorderPlayer(
    private val context: Context,
) : RecorderInterface {

    private var recorder: MediaRecorder? = null
    private var isRecording: Boolean = false
    private var pausedTime: Long = 0

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            recorder = this
            pausedTime = 0
        }
    }

    fun pause() {
        if (isRecording && recorder != null) {
            recorder?.pause()
            pausedTime = SystemClock.elapsedRealtime()
        }
    }

    fun resume() {
        if (!isRecording && recorder != null) {
            recorder?.resume()
            pausedTime = SystemClock.elapsedRealtime() - pausedTime
        }
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }
}