package com.hfad.tagalong.domain.model

data class Rule(
    val id: Long,
    val playlist: Playlist,
    val optionality: Boolean,
    val autoUpdate: Boolean,
    val tags: List<Tag>
) {
    constructor(
        playlist: Playlist,
        optionality: Boolean,
        autoUpdate: Boolean,
        tags: List<Tag>
    ) : this(
        id = 0,
        playlist = playlist,
        optionality = optionality,
        autoUpdate = autoUpdate,
        tags = tags
    )
}