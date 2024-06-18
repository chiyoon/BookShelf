package com.example.bookshelf.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
            // TODO : navigate
        }
        composable(NavigationItem.Search.route) {
            // TODO : navigate
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
        val currentDestination = navBackStackEntry?.destination

        items.forEach { navigationItem ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == navigationItem.route } == true,
                onClick = {
                    navController.navigate(navigationItem.route) {
                        popUpTo(navigationItem.route) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = navigationItem.title),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = navigationItem.icon),
                        contentDescription = stringResource(id = navigationItem.title),
                        modifier = Modifier.size(20.dp)
                    )
                })
        }
    }
}