package com.hfad.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = TAG_TABLE,
    indices = [
        Index(
            value = [TAG_NAME],
            unique = true
        )
    ]
)
data class TagEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TAG_ID)
    val id: Long,

    @ColumnInfo(name = TAG_NAME)
    val name: String

)