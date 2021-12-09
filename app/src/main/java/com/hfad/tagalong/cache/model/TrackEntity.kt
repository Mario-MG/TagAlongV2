package com.hfad.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TRACK_TABLE)
data class TrackEntity(

    @PrimaryKey
    @ColumnInfo(name = TRACK_ID)
    val id: String,

    val name: String,

    val album: String,

    val artists: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?

)