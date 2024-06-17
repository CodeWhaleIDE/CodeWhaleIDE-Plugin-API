package com.bluewhaleyt.codewhaleide.openapi.ui.widget

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.openapi.utils.isLandscape
import com.bluewhaleyt.codewhaleide.openapi.utils.noRippleClickable
import kotlin.math.roundToInt

@Composable
internal fun AnimatedNavigationDrawer(
    modifier: Modifier = Modifier,
    open: Boolean,
    onClose: () -> Unit,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }
    val offsetValue by remember { derivedStateOf { (screenWidth.value / 3.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (open) offsetValue else 0.dp,
        label = "Animated Offset"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (open) 0.9f else 1f,
        label = "Animated Scale"
    )
    BackHandler(enabled = open) {
        onClose()
    }
    AnimatedNavigationDrawer(
        modifier = modifier,
        drawerContent = drawerContent
    ) {
        Scaffold(
            modifier = Modifier
                .offset(x = animatedOffset)
                .scale(scale = animatedScale)
                .noRippleClickable(enabled = open) {
                    onClose()
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                content()
            }
        }
    }
}

@Composable
private fun AnimatedNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            .fillMaxSize()
    ) {
        AnimatedNavigationDrawer(
            modifier = modifier,
            drawerContent = drawerContent
        )
        content()
    }
}

@Composable
private fun AnimatedNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerContent: @Composable ColumnScope.() -> Unit
) {
    val fraction = if (isLandscape()) 0.73f else 0.8f
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction)
            .then(modifier)
    ) {
        drawerContent()
    }
}