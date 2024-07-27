package com.example.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vknewsclient.domain.FeedPost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    favouriteScreenContent: @Composable () -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (feedPost: FeedPost) -> Unit,
    profileScreenContent: @Composable () -> Unit
) {
    NavHost(navController = navHostController, startDestination = Screen.Home.route) {

        homeScreenNavGraph(
            newsFeedScreenContent = newsFeedScreenContent,
            commentsScreenContent = commentsScreenContent
        )

        composable(Screen.Favourite.route) {
            favouriteScreenContent()
        }
        composable(Screen.Profile.route) {
            profileScreenContent()
        }
    }
}