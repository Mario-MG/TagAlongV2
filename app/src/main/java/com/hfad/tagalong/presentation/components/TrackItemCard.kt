package com.hfad.tagalong.presentation.components

import androidx.compose.runtime.Composable
import com.hfad.tagalong.domain.model.Track
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TrackItemCard(
    track: Track,
    onClick: () -> Unit = {}
) {
    ItemCard(
        imageUrl = track.imageUrl,
        title = track.name,
        subtitle = "${track.album} | ${track.artists[0]}", // TODO: Handle multiple artists / no artists?
        onClick = onClick
    )
}