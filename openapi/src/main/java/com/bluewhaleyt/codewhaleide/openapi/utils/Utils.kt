package com.bluewhaleyt.codewhaleide.openapi.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
@Composable
fun isDynamicColorSupported() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@Composable
fun isLandscape() = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

tailrec fun Context.findActivity(): ComponentActivity = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> throw IllegalStateException("No activities can be found")
}