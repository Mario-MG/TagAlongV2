package com.mariomg.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.mariomg.tagalong.cache.*

data class TagEntityPoko(

    @Embedded
    val tagEntity: TagEntity,

    @ColumnInfo(name = TAG_SIZE)
    val size: Int

)