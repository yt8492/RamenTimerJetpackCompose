package com.yt8492.ramentimer.ui.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TimeComponent(timeSeconds: Int) {
    val minutes = timeSeconds / 60
    val seconds = timeSeconds % 60
    val time = "%02d:%02d".format(minutes, seconds)
    Text(text = time)
}