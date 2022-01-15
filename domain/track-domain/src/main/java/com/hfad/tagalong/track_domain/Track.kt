package com.hfad.tagalong.track_domain

data class Track(
    val id: String,
    val name: String,
    val album: String,
    val artists: List<String>,
    val imageUrl: String?,
    val uri: String
)