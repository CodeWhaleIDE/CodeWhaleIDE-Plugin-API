package com.bluewhaleyt.codewhaleide.openapi.ui.component

import android.widget.FrameLayout
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bluewhaleyt.codewhaleide.openapi.action.EditorTextAction
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.ActionIconButton
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.EditorPopupWindow
import io.github.rosemoe.sora.widget.CodeEditor

@Composable
private fun EditorTextActionRow(
    modifier: Modifier = Modifier,
    editor: CodeEditor,
    actions: List<EditorTextAction.Item>
): FrameLayout {
    val textActionWindow = EditorPopupWindow(
        modifier = modifier,
        editor = editor
    ) {
        LazyRow {
            itemsIndexed(
                items = actions,
                key = { i, _ -> i }
            ) { _, action ->
                ActionIconButton(
                    action = action,
                    onClick = action.onClick
                )
            }
        }
    }
    return textActionWindow
}