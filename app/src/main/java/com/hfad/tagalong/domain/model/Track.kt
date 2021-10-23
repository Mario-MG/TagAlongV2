package com.hfad.tagalong.domain.model

data class Track(
    val id: String,
    val name: String,
    val album: String,
    val artists: List<String>,
    val imageUrl: String?
)