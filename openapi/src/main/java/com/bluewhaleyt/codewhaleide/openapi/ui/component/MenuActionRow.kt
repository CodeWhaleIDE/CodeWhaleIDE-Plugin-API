package com.bluewhaleyt.codewhaleide.openapi.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.openapi.action.MenuAction
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Icon
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.toIcon

@Composable
private fun MenuActionRow(
    modifier: Modifier = Modifier,
    actions: List<MenuAction.Item>,
    maxVisibleItems: Int = 0
) {
    val menuItems = remember(actions, maxVisibleItems) {
        splitMenuItems(actions, maxVisibleItems)
    }
    var showMenu by remember { mutableStateOf(false) }
    Row(modifier) {
        menuItems.alwaysShownItems.forEach {
            AnimatedVisibility(visible = it.visible) {
                IconButton(
                    onClick = it.onClick,
                    enabled = it.enabled
                ) {
                    Icon(
                        icon = it.icon,
                        contentDescription = it.label
                    )
                }
            }
        }
        if (menuItems.overflowItems.isNotEmpty()) {
            Box {
                IconButton(
                    onClick = { showMenu = !showMenu }
                ) {
                    Icon(
                        icon = Icons.Default.MoreVert.toIcon(),
                        contentDescription = "More"
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    menuItems.overflowItems.forEach {
                        AnimatedVisibility(visible = it.visible) {
                            DropdownMenuItem(
                                text = {
                                    Text(text = it.label)
                                },
                                leadingIcon = {
                                    Icon(
                                        icon = it.icon,
                                        contentDescription = it.label,
                                        modifier = Modifier.size(16.dp)
                                    )
                                },
                                trailingIcon = {
    //                                if (it.hasNestedActions()) {
    //                                    Icon(
    //                                        icon = Icons.Default.ArrowDropDown.toIcon(),
    //                                        contentDescription = "expand",
    //                                        modifier = Modifier.rotate(-90f)
    //                                    )
    //                                }
                                },
                                onClick = it.onClick,
                                enabled = it.enabled
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class MenuItems(
    val alwaysShownItems: List<MenuAction.Item>,
    val overflowItems: List<MenuAction.Item>,
)

private fun splitMenuItems(
    items: List<MenuAction.Item>,
    maxVisibleItems: Int,
): MenuItems {
    val alwaysShownItems = items.filter { it.showAsAction == MenuAction.ShowAsActionMode.ALWAYS }
        .toMutableList()
    val ifRoomItems = items.filter { it.showAsAction == MenuAction.ShowAsActionMode.IF_ROOM }
        .toMutableList()
    val overflowItems = items.filter { it.showAsAction == MenuAction.ShowAsActionMode.NEVER }
    val hasOverflow = overflowItems.isNotEmpty() ||
            (alwaysShownItems.size + ifRoomItems.size - 1) > maxVisibleItems
    val usedSlots = alwaysShownItems.size + (if (hasOverflow) 1 else 0)
    val availableSlots = maxVisibleItems - usedSlots
    if (availableSlots > 0 && ifRoomItems.isNotEmpty()) {
        val visible = ifRoomItems.subList(0, availableSlots.coerceAtMost(ifRoomItems.size))
        alwaysShownItems.addAll(visible)
        ifRoomItems.removeAll(visible)
    }
    return MenuItems(
        alwaysShownItems = alwaysShownItems,
        overflowItems = ifRoomItems + overflowItems,
    )
}