package com.bluewhaleyt.codewhaleide.openapi.action

import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Content
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Icon

interface NavigationRailAction : Action {

    enum class Position {
        TOP, BOTTOM
    }
    val position: Position get() = Position.TOP
    val pageContent: Content

    data class Item(
        override var label: String,
        override var icon: Icon,
        override var visible: Boolean = true,
        override var enabled: Boolean = true,
        override var position: Position = Position.TOP,
        override val pageContent: Content,
        val onClick: () -> Unit
    ) : NavigationRailAction {
        override fun onClick() {
            onClick.invoke()
        }
    }

}