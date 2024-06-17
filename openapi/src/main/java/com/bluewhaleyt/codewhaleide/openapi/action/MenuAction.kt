package com.bluewhaleyt.codewhaleide.openapi.action

import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Icon

interface MenuAction : Action {

    enum class ShowAsActionMode {
        ALWAYS, IF_ROOM, NEVER
    }
    val showAsAction: ShowAsActionMode get() = ShowAsActionMode.NEVER
//    val nestedActions: List<MenuAction>?

//    fun hasNestedActions() = nestedActions != null

    data class Item(
        override val label: String,
        override val icon: Icon,
        override var visible: Boolean = true,
        override var enabled: Boolean = true,
        override val showAsAction: ShowAsActionMode = ShowAsActionMode.NEVER,
        val onClick: () -> Unit
//        override val nestedActions: List<MenuAction>? = null
    ) : MenuAction {
        override fun onClick() {
            onClick.invoke()
        }
    }

}