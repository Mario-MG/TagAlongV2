package com.mariomg.tagalong.presentation.components

import androidx.compose.runtime.Composable
import com.mariomg.tagalong.presentation.util.DEFAULT_ALBUM_IMAGE
import com.mariomg.tagalong.track_domain.Track
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TrackItemCard(
    track: Track,
    onClick: () -> Unit = {}
) {
    ImageItemCard(
        imageUrl = track.imageUrl,
        defaultImage = DEFAULT_ALBUM_IMAGE,
        title = track.name,
        subtitle = "${track.artists[0]} | ${track.album}", // TODO: Handle multiple artists / no artists?
        onClick = onClick
    )
}