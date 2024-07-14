package com.example.vknewsclient

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknewsclient.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val iconId: ImageVector
) {
    object Home: NavigationItem(
        screen = Screen.NewsFeed,
        titleResId = R.string.navigation_item_main,
        iconId = Icons.Rounded.Home
    )

    object Favourite: NavigationItem(
        screen = Screen.Favourite,
        titleResId = R.string.navigation_item_favourite,
        iconId = Icons.Outlined.Favorite
    )
    object Profile: NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        iconId = Icons.Outlined.Person
    )
}