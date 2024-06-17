package com.bluewhaleyt.codewhaleide.openapi.action

import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Content
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Icon

interface EditorTabAction : Action {

    val pageContent: Content

    data class Item(
        override val label: String,
        override val icon: Icon,
        override val visible: Boolean = true,
        override val enabled: Boolean = true,
        override val pageContent: Content,
        val onClick: () -> Unit
    ) : EditorTabAction {
        override fun onClick() {
            onClick.invoke()
        }
    }

}