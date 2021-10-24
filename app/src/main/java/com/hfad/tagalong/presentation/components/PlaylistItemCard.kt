package com.hfad.tagalong.presentation.components

import androidx.compose.runtime.Composable
import com.hfad.tagalong.domain.model.Playlist
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PlaylistItemCard(
    playlist: Playlist,
    onClick: () -> Unit = {}
) {
    ItemCard(
        imageUrl = playlist.imageUrl,
        title = playlist.name,
        subtitle = "${playlist.size} songs", // TODO: Handle singular/plural
        onClick = onClick
    )
}