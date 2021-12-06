package com.hfad.tagalong.domain.model

data class Playlist(
    val id: String,
    val name: String,
    val size: Int,
    val imageUrl: String? = null
) {
    constructor(name: String) : this(
        id = "",
        name = name,
        size = 0
    )
}
