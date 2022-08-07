package com.mariomg.tagalong.rule_domain

import com.mariomg.tagalong.playlist_domain.Playlist
import com.mariomg.tagalong.tag_domain.Tag

data class RuleInfo(
    val playlist: Playlist,
    val optionality: Boolean,
    val autoUpdate: Boolean,
    val tags: List<Tag>
)
