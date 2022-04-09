package com.hfad.tagalong.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.hfad.tagalong.track_domain.Track
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TrackItemList(
    tracks: List<Track>,
    loading: Boolean,
    onTriggerNextPage: () -> Unit = {},
    onNavigateToTrackDetail: (Track) -> Unit
) {
    LazyColumn {
        itemsIndexed(items = tracks) { index, track ->
            if (index + 1 >= tracks.size && !loading) {
                onTriggerNextPage()
            }
            TrackItemCard(
                track = track,
                onClick = {
                    onNavigateToTrackDetail(track)
                }
            )
        }
    }
}