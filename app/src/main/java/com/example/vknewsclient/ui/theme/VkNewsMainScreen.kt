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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vknewsclient.NavigationItem
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.navigation.AppNavGraph
import com.example.vknewsclient.navigation.rememberNavigationState


@Composable
fun MainScreen() {
    val navigationItems =
        listOf(NavigationItem.Home, NavigationItem.Favourite, NavigationItem.Profile)
    val navigationState = rememberNavigationState()

    val tempState: MutableState<FeedPost?> = remember {
        mutableStateOf(null)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navHostController = navigationState.navHostController,
                items = navigationItems,
                onItemSelected = { item ->
                    navigationState.navigateTo(item.screen.route)
                }
            )
        }
    )
    { innerPadding ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                HomeScreen(
                    innerPadding = innerPadding,
                    onCommentsClickListener = {
                        tempState.value = it
                        navigationState.navigateToComments()
                    }
                )
            },
            commentsScreenContent = {
                CommentsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    feedPost = tempState.value!!
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
        items.forEach { item ->
            val selected = navBackStackEntry?.destination?.hierarchy?.any {
                (it.route == item.screen.route)
            } ?: false
            val scale by animateFloatAsState(if (selected) 1.2f else 1.0f)
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector =
                        if (selected) item.iconIdRounded
                        else item.iconIdOutlined,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp * scale)
                    )
                },
                label = { Text(stringResource(id = item.titleResId)) },
                selected = selected,
                onClick = {
                    if (!selected) {
                        onItemSelected(item)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}

