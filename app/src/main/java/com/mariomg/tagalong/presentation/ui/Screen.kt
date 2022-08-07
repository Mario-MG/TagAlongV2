package com.mariomg.tagalong.presentation.ui

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import com.mariomg.tagalong.R
import com.mariomg.tagalong.presentation.BaseApplication

enum class Screen(
    val icon: @Composable () -> Unit,
    @StringRes val labelRes: Int,
    @IdRes val destination: Int
) {

    Playlists(
        icon = { Icon(Icons.Filled.QueueMusic, contentDescription = BaseApplication.getString(R.string.playlists_icon_description)) },
        labelRes = R.string.playlists_screen_label,
        destination = R.id.playlistsFragment
    ),

    Tags(
        icon = { Icon(Icons.Filled.Tag, contentDescription = BaseApplication.getString(R.string.tags_icon_description)) },
        labelRes = R.string.tags_screen_label,
        destination = R.id.tagsFragment
    ),

    Rules(
        icon = { Icon(Icons.Filled.PlaylistAdd, contentDescription = BaseApplication.getString(R.string.rules_icon_description)) },
        labelRes = R.string.rules_screen_label,
        destination = R.id.rulesFragment
    ),

    Settings(
        icon = { Icon(Icons.Filled.Settings, contentDescription = BaseApplication.getString(R.string.settings_icon_description)) },
        labelRes = R.string.settings_screen_label,
        destination = R.id.settingsFragment
    );

    fun getLabel(): String = BaseApplication.getString(labelRes)

}