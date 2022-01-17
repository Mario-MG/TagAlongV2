package com.hfad.tagalong.rule_domain

import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.tag_domain.Tag

data class Rule(
    val id: Long,
    val playlist: Playlist,
    val optionality: Boolean,
    val autoUpdate: Boolean,
    val tags: List<Tag>
)