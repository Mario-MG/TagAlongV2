package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.hfad.tagalong.domain.model.Playlist
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PlaylistItemList(
    playlists: List<Playlist>,
    loading: Boolean,
    onTriggerNextPage: () -> Unit,
    onNavigateToTrackList: (Playlist) -> Unit
) {
    LazyColumn {
        itemsIndexed(items = playlists) { index, playlist ->
            if (index + 1 >= playlists.size && !loading) {
                onTriggerNextPage()
            }
            PlaylistItemCard(
                playlist = playlist,
                onClick = {
                    onNavigateToTrackList(playlist)
                }
            )
        }
    }
}