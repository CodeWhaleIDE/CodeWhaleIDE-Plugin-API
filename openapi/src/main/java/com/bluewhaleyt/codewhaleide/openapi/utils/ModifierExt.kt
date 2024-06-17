package com.bluewhaleyt.codewhaleide.openapi.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.applyIf(
    predicate: Boolean,
    other: @Composable Modifier.() -> Modifier
) = composed { if (predicate) other() else this }

fun Modifier.edgeToEdgePadding(
    withStatusBar: Boolean = true,
    withNavigationBar: Boolean = true
)  = composed { this
    .applyIf(withStatusBar) { statusBarsPadding() }
    .applyIf(withNavigationBar) { navigationBarsPadding() }
}

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        enabled = enabled,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.combinedClickable(
    enabled: Boolean,
    onClick: (() -> Unit)?,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
) = composed {
    then(
        if (enabled) {
            if (onClick != null && onDoubleClick != null && onLongClick != null) {
                Modifier.combinedClickable(
                    onClick = onClick,
                    onDoubleClick = onDoubleClick,
                    onLongClick = onLongClick
                )
            } else if (onClick != null) {
                Modifier.clickable { onClick() }
            } else if (onDoubleClick != null) {
                Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            onDoubleClick()
                        }
                    )
                }
            } else if (onLongClick != null) {
                Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongClick()
                        }
                    )
                }
            } else Modifier
        } else Modifier
    )
}