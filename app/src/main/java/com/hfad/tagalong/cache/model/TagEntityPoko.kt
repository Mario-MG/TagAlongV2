package com.hfad.tagalong.cache.model

import androidx.room.Embedded

data class TagEntityPoko (
    @Embedded val tagEntity: TagEntity,
    val size: Int
)