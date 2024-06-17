package com.bluewhaleyt.codewhaleide.openapi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.bluewhaleyt.codewhaleide.openapi.action.NavigationRailAction
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.AnimatedNavigationDrawer
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Content
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.Icon
import com.bluewhaleyt.codewhaleide.openapi.ui.widget.toIcon

@Composable
private fun NavigationRailActionPageDrawer(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    open: Boolean,
    actions: List<NavigationRailAction.Item>,
    selectedAction: NavigationRailAction.Item,
    onActionSelected: (NavigationRailAction.Item) -> Unit,
    onActionReselected: (NavigationRailAction.Item) -> Unit = {},
    onClose: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationRailActionDrawer(
        modifier = modifier,
        open = open,
        actions = actions,
        selectedAction = selectedAction,
        onActionSelected = onActionSelected,
        onActionReselected = onActionReselected,
        onClose = onClose,
        content = content,
        drawerContentNextToNavigationRail = {
            VerticalPager(
                state = pagerState,
                beyondViewportPageCount = pagerState.pageCount,
                userScrollEnabled = false,
                key = { it }
            ) { index ->
                Content(content = actions[index].pageContent)
            }
            LaunchedEffect(key1 = selectedAction) {
                pagerState.scrollToPage(actions.indexOf(selectedAction))
            }
        }
    )
}

@Composable
private fun NavigationRailActionDrawer(
    modifier: Modifier = Modifier,
    open: Boolean,
    actions: List<NavigationRailAction.Item>,
    selectedAction: NavigationRailAction.Item,
    onActionSelected: (NavigationRailAction.Item) -> Unit,
    onActionReselected: (NavigationRailAction.Item) -> Unit,
    onClose: () -> Unit,
    drawerContentNextToNavigationRail: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    AnimatedNavigationDrawer(
        modifier = modifier,
        open = open,
        onClose = onClose,
        content = content,
        drawerContent = {
            Row {
                Column {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp))
                            .width(60.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = onClose) {
                            Icon(
                                icon = Icons.AutoMirrored.Filled.ArrowBack.toIcon(),
                                contentDescription = "Back"
                            )
                        }
                    }
                    NavigationRail(
                        modifier = Modifier.width(60.dp),
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
                    ) {
                        actions.filter { it.position == NavigationRailAction.Position.TOP }
                            .forEach { action ->
                                NavigationRailActionItem(
                                    action = action,
                                    selected = action == selectedAction,
                                    onActionSelected = {
                                        if (action == selectedAction) onActionReselected(it)
                                        else onActionSelected(it)
                                    }
                                )
                            }
                        Spacer(modifier = Modifier.weight(1f))
                        actions.filter { it.position == NavigationRailAction.Position.BOTTOM }
                            .forEach { action ->
                                NavigationRailActionItem(
                                    action = action,
                                    selected = action == selectedAction,
                                    onActionSelected = {
                                        if (action == selectedAction) onActionReselected(it)
                                        else onActionSelected(it)
                                    }
                                )
                            }
                    }
                }
                drawerContentNextToNavigationRail()
            }
        }
    )
}

@Composable
private fun NavigationRailActionItem(
    modifier: Modifier = Modifier,
    action: NavigationRailAction.Item,
    selected: Boolean,
    onActionSelected: (NavigationRailAction.Item) -> Unit
) {
    NavigationRailItem(
        modifier = modifier.scale(0.8f),
        selected = selected,
        onClick = {
            action.onClick.invoke()
            onActionSelected(action)
        },
        icon = {
            Icon(
                icon = action.icon,
                contentDescription = action.label
            )
        },
        enabled = action.enabled
    )
}