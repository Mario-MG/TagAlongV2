package com.hfad.tagalong.playlist_domain

data class PlaylistInfo(
    val name: String,
    val size: Int, // TODO: Should this field be part of the domain?
    val imageUrl: String? = null
)