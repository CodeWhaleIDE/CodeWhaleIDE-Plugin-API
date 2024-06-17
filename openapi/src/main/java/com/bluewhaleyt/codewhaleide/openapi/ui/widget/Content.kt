package com.bluewhaleyt.codewhaleide.openapi.ui.widget

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

interface Content

data class ViewContent internal constructor(
    val view: View
) : Content

data class ComposeContent internal constructor(
    val content: @Composable () -> Unit
) : Content

fun setContentView(view: View) = ViewContent(view)

fun setContent(content: @Composable () -> Unit) = ComposeContent(content)

@Composable
fun Content(
    modifier: Modifier = Modifier,
    content: Content
) {
    when (content) {
        is ViewContent -> {
            AndroidView(
                modifier = modifier,
                factory = { content.view }
            )
        }
        is ComposeContent -> {
            Box(modifier) {
                content.content()
            }
        }
    }
}