package com.bluewhaleyt.codewhaleide.openapi.ui.widget

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import io.github.rosemoe.sora.widget.CodeEditor

@Composable
internal fun EditorPopupWindow(
    modifier: Modifier = Modifier,
    editor: CodeEditor?,
    content: @Composable () -> Unit
): FrameLayout {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val savedStateRegistryOwner = LocalSavedStateRegistryOwner.current
    val composeView = ComposeView(context).apply {
        setContent {
//            Theme {
                editor?.let {
                    Card(
                        modifier = modifier,
//                        colors = CardDefaults.cardColors(
//                            containerColor = Color(it.colorScheme.getColor(EditorColorScheme.CURRENT_LINE)),
//                            contentColor = Color(it.colorScheme.getColor(EditorColorScheme.TEXT_NORMAL)),
//                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        content()
                    }
                }
//            }
        }
    }
    val parentView = FrameLayout(context).apply {
        id = android.R.id.content
        setViewTreeLifecycleOwner(lifecycleOwner)
        setViewTreeSavedStateRegistryOwner(savedStateRegistryOwner)
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(composeView)
    }
    return parentView
}