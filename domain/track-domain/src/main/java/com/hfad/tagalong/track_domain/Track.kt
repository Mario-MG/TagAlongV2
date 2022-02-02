package com.hfad.tagalong.track_domain

data class Track(
    val id: String,
    val info: TrackInfo
) {
    constructor(
        id: String,
        name: String,
        album: String,
        artists: List<String>,
        imageUrl: String?,
        uri: String
    ) : this(
        id = id,
        info = TrackInfo(
            name = name,
            album = album,
            artists = artists,
            imageUrl = imageUrl,
            uri = uri
        )
    )

    val name: String
        get() = this.info.name

    val album: String
        get() = this.info.album

    val artists: List<String>
        get() = this.info.artists

    val imageUrl: String?
        get() = this.info.imageUrl

    val uri: String
        get() = this.info.uri
}