package com.example.vknewsclient

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknewsclient.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val iconIdRounded: ImageVector,
    val iconIdOutlined: ImageVector
) {
    object Home: NavigationItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_main,
        iconIdRounded = Icons.Rounded.Home,
        iconIdOutlined = Icons.Outlined.Home
    )

    object Favourite: NavigationItem(
        screen = Screen.Favourite,
        titleResId = R.string.navigation_item_favourite,
        iconIdRounded = Icons.Rounded.Favorite,
        iconIdOutlined = Icons.Outlined.FavoriteBorder
    )
    object Profile: NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        iconIdRounded = Icons.Rounded.Person,
        iconIdOutlined = Icons.Outlined.Person
    )
}