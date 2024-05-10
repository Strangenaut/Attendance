package com.strangenaut.attendance.statistics.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.core.navigation.util.setScreen
import com.strangenaut.attendance.core.presentation.components.TopBar
import com.strangenaut.attendance.statistics.presentation.model.TextTabBarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun TextTabBar(
    currentTabIndex: MutableStateFlow<Int>,
    textTabBarItems: List<TextTabBarItem>,
    navController: NavHostController
) {
    val collectedState = currentTabIndex.collectAsState()
    val currentTabIndexRemember by remember {
        mutableStateOf(collectedState)
    }

    TopBar {
        Row {
            textTabBarItems.forEachIndexed { index, tabBarItem ->
                TextTab(
                    title = tabBarItem.title,
                    selected = currentTabIndexRemember.value == index,
                    onClick = {
                        currentTabIndex.update {
                            index
                        }
                        navController.setScreen(tabBarItem.route)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun TextTabBarPreview() {
    val currentTabIndex = MutableStateFlow(0)
    val textTabBarItems = listOf(
        TextTabBarItem(title = "History", route = "history"),
        TextTabBarItem(title = "Statistics", route = "statistics")
    )
    val navController = rememberNavController()

    TextTabBar(
        currentTabIndex = currentTabIndex,
        textTabBarItems = textTabBarItems,
        navController = navController
    )
}