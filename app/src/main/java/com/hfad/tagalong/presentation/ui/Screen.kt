package com.hfad.tagalong.presentation.ui

import androidx.annotation.IdRes
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import com.hfad.tagalong.R

enum class Screen(
    val icon: @Composable () -> Unit,
    val label: String,
    @IdRes val destination: Int
) {

    Playlists(
        icon = { Icon(Icons.Filled.QueueMusic, contentDescription = "Playlists icon") },
        label = "Playlists",
        destination = R.id.playlistsFragment
    ),

    Tags(
        icon = { Icon(Icons.Filled.Tag, contentDescription = "Tags icon") },
        label = "Tags",
        destination = R.id.tagsFragment
    ),

    Rules(
        icon = { Icon(Icons.Filled.PlaylistAdd, contentDescription = "Rules icon") },
        label = "Rules",
        destination = R.id.rulesFragment
    ),

    Settings(
        icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings icon") },
        label = "Settings",
        destination = R.id.settingsFragment
    )

}