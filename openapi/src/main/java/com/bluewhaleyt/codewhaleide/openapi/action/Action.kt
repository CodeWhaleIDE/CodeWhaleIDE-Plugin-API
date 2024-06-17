package com.bluewhaleyt.codewhaleide.openapi.action

import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Icon

interface Action {

    val label: String
    val icon: Icon
    val visible: Boolean get() = true
    val enabled: Boolean get() = true

    fun onClick()

    data class Item(
        override var label: String,
        override var icon: Icon,
        override var visible: Boolean = true,
        override var enabled: Boolean = true,
        val onClick: () -> Unit
    ) : Action {
        override fun onClick() {
            onClick.invoke()
        }
    }

}