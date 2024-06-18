package com.example.bookshelf.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bookshelf.ui.screen.NewScreen
import com.example.bookshelf.ui.screen.SearchScreen
import com.example.bookshelf.ui.theme.Purple40
import com.example.composetest.NavigationItem

@Composable
fun BookShelfNavigationConfigurations(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.New.route,
        modifier = Modifier
            .padding(innerPadding)
    ) {
        composable(NavigationItem.New.route) {
            NewScreen()
        }
        composable(NavigationItem.Search.route) {
            SearchScreen()
        }
    }
}

@Composable
fun BookShelfNavigation(
    navController: NavHostController,
    items: List<NavigationItem>
) {
    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val selectedColor = Purple40
        val currentDestination = navBackStackEntry?.destination

        items.forEach { navigationItem ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == navigationItem.route } == true
            val itemColor = if (isSelected) selectedColor else Color.Black

            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = navigationItem.icon),
                            contentDescription = stringResource(id = navigationItem.title),
                            modifier = Modifier
                                .size(24.dp)
                                .offset(y = 4.dp),
                            tint = itemColor
                        )
                        Text(
                            text = stringResource(id = navigationItem.title),
                            color = itemColor,
                            fontSize = 16.sp,
                            modifier = Modifier.offset(y = 4.dp)
                        )
                    }
                },
                selectedContentColor = selectedColor,
            )
        }
    }
}