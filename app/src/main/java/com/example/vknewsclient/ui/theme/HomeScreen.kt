package com.example.vknewsclient.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.HomeScreenState
import com.example.vknewsclient.MainViewModel
import com.example.vknewsclient.domain.FeedPost


@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    innerPadding: PaddingValues
) {

    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Posts(listOf()))
    when (val currentState = screenState.value) {
        is HomeScreenState.Posts -> {
            FeedPosts(
                viewModel = viewModel,
                innerPadding = innerPadding,
                posts = currentState.posts
            )
        }

        is HomeScreenState.Comments -> {
            CommentsScreen(feedPost = currentState.feedPost, comments = currentState.comments) {
                viewModel.changeState(feedPost = currentState.feedPost)
            }
            BackHandler {
                viewModel.changeState(feedPost = currentState.feedPost)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FeedPosts(
    viewModel: MainViewModel,
    innerPadding: PaddingValues,
    posts: List<FeedPost>
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
            items(posts, key = { it.id }) { model ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        when (it) {
                            StartToEnd -> {
                                viewModel.deleteFeedPost(model)
                            }

                            EndToStart -> {
                                viewModel.deleteFeedPost(model)
                            }

                            Settled -> return@rememberSwipeToDismissBoxState false
                        }
                        return@rememberSwipeToDismissBoxState true
                    }
                )
                SwipeToDismissBox(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    backgroundContent = { DismissBackground(dismissState = dismissState) }) {
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
                            viewModel.changeState(model)
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        StartToEnd -> Color(0xFFFF1744)
        EndToStart -> Color(0xFFFF1744)
        Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
        Spacer(modifier = Modifier)
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
    }
}