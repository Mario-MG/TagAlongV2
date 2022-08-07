package com.mariomg.tagalong.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mariomg.tagalong.R
import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.presentation.util.DEFAULT_ALBUM_IMAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PlaylistItemCard(
    playlist: Playlist,
    onClick: () -> Unit = {}
) {
    val resources = LocalContext.current.resources

    ImageItemCard(
        imageUrl = playlist.imageUrl,
        defaultImage = DEFAULT_ALBUM_IMAGE,
        title = playlist.name,
        subtitle = resources.getQuantityString(
            R.plurals.number_of_songs,
            playlist.size,
            playlist.size
        ),
        onClick = onClick
    )
}