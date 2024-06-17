package com.bluewhaleyt.codewhaleide.openapi.action

import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Icon

interface EditorTextAction : Action {

    data class Item(
        override val label: String,
        override val icon: Icon,
        override val visible: Boolean = true,
        override val enabled: Boolean = true,
        val onClick: () -> Unit
    ) : EditorTextAction {
        override fun onClick() {
            onClick.invoke()
        }
    }

}