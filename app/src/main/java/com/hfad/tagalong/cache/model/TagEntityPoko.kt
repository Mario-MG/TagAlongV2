package com.hfad.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class TagEntityPoko (

    @Embedded
    val tagEntity: TagEntity,

    @ColumnInfo(name = TAG_SIZE)
    val size: Int

)