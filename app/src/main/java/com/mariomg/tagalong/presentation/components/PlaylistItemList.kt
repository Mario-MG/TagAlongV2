package com.mariomg.tagalong.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.mariomg.tagalong.playlist_domain.Playlist
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PlaylistItemList(
    playlists: List<Playlist>,
    loading: Boolean,
    onTriggerNextPage: () -> Unit,
    onClickPlaylistItem: (Playlist) -> Unit
) {
    LazyColumn {
        itemsIndexed(items = playlists) { index, playlist ->
            if (index + 1 >= playlists.size && !loading) {
                onTriggerNextPage()
            }
            PlaylistItemCard(
                playlist = playlist,
                onClick = {
                    onClickPlaylistItem(playlist)
                }
            )
        }
    }
}