package com.hfad.tagalong.presentation.ui

import androidx.annotation.IdRes
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import com.hfad.tagalong.R

sealed class Screen(
    val icon: @Composable () -> Unit,
    val label: String,
    @IdRes val route: Int
) {

    object Playlists : Screen(
        icon = { Icon(Icons.Filled.QueueMusic, contentDescription = "Playlists icon") },
        label = "Playlists",
        route = R.id.playlistsFragment
    )

    object Tags : Screen(
        icon = { Icon(Icons.Filled.Tag, contentDescription = "Tags icon") },
        label = "Tags",
        route = R.id.tagsFragment
    )

    object Settings : Screen(
        icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings icon") },
        label = "Settings",
        route = R.id.settingsFragment
    )

}