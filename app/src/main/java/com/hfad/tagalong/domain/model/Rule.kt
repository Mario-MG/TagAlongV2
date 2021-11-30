package com.hfad.tagalong.domain.model

data class Rule(
    val id: Long,
    val playlistId: String,
    val optionality: Boolean,
    val autoUpdate: Boolean,
    val tags: List<Tag>
) {
    constructor(
        playlistId: String,
        optionality: Boolean,
        autoUpdate: Boolean,
        tags: List<Tag>
    ) : this(
        id = 0,
        playlistId = playlistId,
        optionality = optionality,
        autoUpdate = autoUpdate,
        tags = tags
    )
}