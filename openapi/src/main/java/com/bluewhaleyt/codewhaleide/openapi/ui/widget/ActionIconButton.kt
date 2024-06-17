package com.bluewhaleyt.codewhaleide.openapi.ui.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codewhaleide.openapi.action.Action

@Composable
fun ActionIconButton(
    modifier: Modifier = Modifier,
    action: Action,
    onClick: () -> Unit
) {
    AnimatedVisibility(visible = action.visible) {
        IconButton(
            modifier = modifier,
            onClick = onClick,
            enabled = action.enabled
        ) {
            Icon(
                icon = action.icon,
                contentDescription = action.label
            )
        }
    }
}

@Composable
fun BoxActionIconButton(
    modifier: Modifier = Modifier,
    action: Action,
    onClick: () -> Unit
) {
    AnimatedVisibility(visible = action.visible) {
        Box(
            modifier = Modifier
                .clickable(action.enabled) {
                    onClick()
                }
                .minimumInteractiveComponentSize()
                .then(modifier)
        ) {
            Icon(
                icon = action.icon,
                contentDescription = action.label,
                tint = if (action.enabled) LocalContentColor.current
                else LocalContentColor.current.copy(0.38f)
            )
        }
    }
}