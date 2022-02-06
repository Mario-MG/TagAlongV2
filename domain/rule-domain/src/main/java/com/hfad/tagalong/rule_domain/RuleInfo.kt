package com.hfad.tagalong.rule_domain

import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.tag_domain.Tag

data class RuleInfo(
    val playlist: Playlist,
    val optionality: Boolean,
    val autoUpdate: Boolean,
    val tags: List<Tag>
)
