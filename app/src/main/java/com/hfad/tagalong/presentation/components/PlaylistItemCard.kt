package com.hfad.tagalong.presentation.components

import androidx.compose.runtime.Composable
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.util.DEFAULT_ALBUM_IMAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PlaylistItemCard(
    playlist: Playlist,
    onClick: () -> Unit = {}
) {
    ImageItemCard(
        imageUrl = playlist.imageUrl,
        defaultImage = DEFAULT_ALBUM_IMAGE,
        title = playlist.name,
        subtitle = "${playlist.size} songs", // TODO: Handle singular/plural
        onClick = onClick
    )
}