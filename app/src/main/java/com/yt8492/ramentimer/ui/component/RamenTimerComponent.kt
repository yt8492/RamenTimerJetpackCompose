package com.yt8492.ramentimer.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RamenTimerComponent() {
    val composableScope = rememberCoroutineScope()
    var timerSeconds by remember {
        mutableStateOf(0)
    }
    var timerState by remember {
        mutableStateOf(TimerState(0))
    }

    fun increment(seconds: Int) {
        timerSeconds += seconds
        timerState = timerState.increment(seconds)
    }

    fun startTimer() {
        timerState = timerState.copy(isStop = false)
        composableScope.launch {
            repeat(timerState.remainSeconds) {
                if (timerState.isStop) {
                    return@launch
                }
                delay(1000)
                timerState = timerState.decrement1secondsOrStop()
            }
        }
    }

    Column {
        TimeComponent(timeSeconds = timerState.remainSeconds)
        Row {
            Button(onClick = { increment(60 * 10) }) {
                Text(text = "+ 10分")
            }
            Button(onClick = { increment(60) }) {
                Text(text = "+ 1分")
            }
            Button(onClick = { increment(10) }) {
                Text(text = "+ 10秒")
            }
        }
        if (timerState.isStop) {
            Button(onClick = ::startTimer) {
                Text(text = "start")
            }
        } else {
            Button(onClick = { timerState = timerState.copy(0, true) }) {
                Text(text = "reset")
            }
        }
    }
}

data class TimerState(
    val remainSeconds: Int,
    val isStop: Boolean = true
) {
    fun decrement1secondsOrStop(): TimerState {
        return if (remainSeconds > 0) {
            copy(
                remainSeconds = remainSeconds - 1
            )
        } else {
            copy(
                remainSeconds = 0,
                isStop = true
            )
        }
    }

    fun increment(seconds: Int): TimerState {
        return copy(
            remainSeconds = remainSeconds + seconds
        )
    }
}
