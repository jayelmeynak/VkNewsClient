package com.example.vknewsclient

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
    val navigationItems =
        listOf(NavigationItem.Home, NavigationItem.Favourite, NavigationItem.Profile)
    val listFeedPost = viewModel.feedPosts.observeAsState(listOf())
    var selectedItem by remember { mutableStateOf<NavigationItem>(NavigationItem.Home) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(items = navigationItems,
                selectedItem = selectedItem,
                onItemSelected = { item ->
                    selectedItem = item
                }
            )
        }
    )
    { innerPadding ->
        Content(viewModel = viewModel, innerPadding = innerPadding, listFeedPost = listFeedPost)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Content(
    viewModel: MainViewModel,
    innerPadding: PaddingValues,
    listFeedPost: State<List<FeedPost>>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listFeedPost.value, key = { it.id }) { model ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.deleteFeedPost(model)
                        }
                        return@rememberSwipeToDismissBoxState true
                    }
                )
                SwipeToDismissBox(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    backgroundContent = {  }) {
                    PostCard(
                        feedPost = model,
                        onViewsClickListener = { statisticItem ->
                            viewModel.changeStatistics(model, statisticItem)
                        },
                        onLikeClickListener = { statisticItem ->
                            viewModel.changeStatistics(model, statisticItem)
                        },
                        onShareClickListener = { statisticItem ->
                            viewModel.changeStatistics(model, statisticItem)
                        },
                        onCommentClickListener = { statisticItem ->
                            viewModel.changeStatistics(model, statisticItem)
                        },
                    )
                }
            }
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

