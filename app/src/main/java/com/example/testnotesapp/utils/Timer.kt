package com.example.testnotesapp.utils

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.*

// przyda się to do wyeliminowania błedów związanych z szybkim zapisem wielu notatek (cooldown)
class Timer {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private fun startCoroutineTimer(delayMillis: Long = 0, repeatMillis: Long = 0, onEnd: () -> Unit={},whileCounting: () -> Unit={}) = scope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                whileCounting()
                delay(repeatMillis)
            }
        } else {
            onEnd()

            Log.d("TESTqwd","koniec")
        }
    }

    private val timer: Job = startCoroutineTimer(delayMillis = 0, repeatMillis = 20000) {
        scope.launch(Dispatchers.Main) {
        }
    }

    fun startTimer() {
        timer.start()
    }

    fun cancelTimer() {
        timer.cancel()
    }
}