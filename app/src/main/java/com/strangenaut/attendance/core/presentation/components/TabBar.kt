package com.strangenaut.attendance.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.strangenaut.attendance.core.components.HorizontalLine
import com.strangenaut.attendance.core.presentation.model.TabBarItem
import com.strangenaut.attendance.core.navigation.util.setScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun TabBar(
    currentTabIndex: MutableStateFlow<Int>,
    tabBarItems: List<TabBarItem>,
    navController: NavHostController
) {
    val collectedState = currentTabIndex.collectAsState()
    val currentTabIndexRemember by remember {
        mutableStateOf(collectedState)
    }

    val navBarItemColors = NavigationBarItemDefaults.colors (
        indicatorColor = MaterialTheme.colorScheme.background,
    )

    Column {
        HorizontalLine(
            height = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxHeight(0.075f)
        ) {
            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    colors = navBarItemColors,
                    selected = currentTabIndexRemember.value == index,
                    onClick = {
                        currentTabIndex.update {
                            index
                        }
                        navController.setScreen(tabBarItem.title)
                    },
                    icon = {
                        Image(
                            painter = if (currentTabIndexRemember.value == index)
                                tabBarItem.selectedIcon
                            else
                                tabBarItem.unselectedIcon,
                            modifier = Modifier.fillMaxHeight(0.5f),
                            contentDescription = tabBarItem.title
                        )
                    },
                    label = {}
                )
            }
        }
    }
}