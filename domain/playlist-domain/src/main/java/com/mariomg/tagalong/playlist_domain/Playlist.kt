package com.mariomg.tagalong.playlist_domain

data class Playlist(
    val id: String,
    private val info: PlaylistInfo
) {
    constructor(
        id: String,
        name: String,
        size: Int,
        imageUrl: String?
    ) : this(
        id = id,
        info = PlaylistInfo(
            name = name,
            size = size,
            imageUrl = imageUrl
        )
    )

    val name: String
        get() = this.info.name

    val size: Int
        get() = this.info.size

    val imageUrl: String?
        get() = this.info.imageUrl
}