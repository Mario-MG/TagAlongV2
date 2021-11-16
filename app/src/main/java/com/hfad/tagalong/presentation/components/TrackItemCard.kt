package com.hfad.tagalong.presentation.components

import androidx.compose.runtime.Composable
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.util.DEFAULT_ALBUM_IMAGE
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
        subtitle = "${track.album} | ${track.artists[0]}", // TODO: Handle multiple artists / no artists?
        onClick = onClick
    )
}