package com.hfad.tagalong.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.hfad.tagalong.R
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.presentation.util.DEFAULT_ALBUM_IMAGE
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
        subtitle = resources.getQuantityString(R.plurals.number_of_songs, playlist.size, playlist.size),
        onClick = onClick
    )
}