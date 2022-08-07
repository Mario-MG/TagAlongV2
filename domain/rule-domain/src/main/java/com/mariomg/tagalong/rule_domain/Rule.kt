package com.mariomg.tagalong.rule_domain

import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.tag_domain.Tag

data class Rule(
    val id: Long,
    private val info: RuleInfo
) {
    constructor(
        id: Long,
        playlist: Playlist,
        optionality: Boolean,
        autoUpdate: Boolean,
        tags: List<Tag>
    ) : this(
        id = id,
        info = RuleInfo(
            playlist = playlist,
            optionality = optionality,
            autoUpdate = autoUpdate,
            tags = tags
        )
    )

    val playlist: Playlist
        get() = this.info.playlist

    val optionality: Boolean
        get() = this.info.optionality

    val autoUpdate: Boolean
        get() = this.info.autoUpdate

    val tags: List<Tag>
        get() = this.info.tags
}