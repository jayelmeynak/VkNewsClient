package com.example.vknewsclient

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.domain.FeedPost


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val feedPost = viewModel.feedPost.observeAsState(FeedPost())
    val items = listOf(NavigationItem.Home, NavigationItem.Favourite, NavigationItem.Profile)
    var selectedItem by remember { mutableStateOf<NavigationItem>(NavigationItem.Home) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(items = items,
                selectedItem = selectedItem,
                onItemSelected = { item ->
                    selectedItem = item
                }
            )
        }
    )
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)
        ) {
            PostCard(
                innerPadding,
                feedPost = feedPost.value,
                onViewsClickListener = { statisticItem ->
                    viewModel.changeStatistics(statisticItem)
                },
                onLikeClickListener = { statisticItem ->
                    viewModel.changeStatistics(statisticItem)
                },
                onShareClickListener = { statisticItem ->
                    viewModel.changeStatistics(statisticItem)
                },
                onCommentClickListener = { statisticItem ->
                    viewModel.changeStatistics(statisticItem)
                },
            )
        }
    }
}


@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    selectedItem: NavigationItem,
    onItemSelected: (NavigationItem) -> Unit
) {
    NavigationBar {
        items.forEach { item ->
            val selected = selectedItem == item
            val scale by animateFloatAsState(if (selected) 1.2f else 1.0f)
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.iconId,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp * scale)
                    )
                },
                label = { Text(stringResource(id = item.titleResId)) },
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}

