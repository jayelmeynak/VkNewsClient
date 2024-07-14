package com.example.vknewsclient.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vknewsclient.MainViewModel
import com.example.vknewsclient.NavigationItem
import com.example.vknewsclient.navigation.AppNavGraph


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navigationItems =
        listOf(NavigationItem.Home, NavigationItem.Favourite, NavigationItem.Profile)
    val listFeedPost = viewModel.feedPosts.observeAsState(listOf())
    val navHostController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navHostController = navHostController,
                items = navigationItems,
                onItemSelected = { item ->
                    navHostController.navigate(item.screen.route)
                }
            )
        }
    )
    { innerPadding ->
        AppNavGraph(
            navHostController = navHostController,
            homeScreenContent = {
                HomeScreen(
                    viewModel = viewModel,
                    innerPadding = innerPadding,
                    listFeedPost = listFeedPost
                )
            },
            favouriteScreenContent = {
                Box(modifier = Modifier.padding(innerPadding)) {
                    Text(text = "Favourite screen")
                }
            },
            profileScreenContent = {
                Box(modifier = Modifier.padding(innerPadding)) {
                    Text(text = "Profile screen")
                }
            }
        )
    }
}


@Composable
fun BottomNavigationBar(
    navHostController: NavHostController,
    items: List<NavigationItem>,
    onItemSelected: (NavigationItem) -> Unit
) {
    NavigationBar {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val selected = currentRoute == item.screen.route
            val scale by animateFloatAsState(if (selected) 1.2f else 1.0f, label = "Hello")
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.iconId,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp * scale)
                    )
                },
                label = { Text(stringResource(id = item.titleResId)) },
                selected = currentRoute == item.screen.route,
                onClick = { onItemSelected(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}

