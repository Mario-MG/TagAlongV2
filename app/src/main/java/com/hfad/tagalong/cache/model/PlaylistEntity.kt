package com.hfad.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PLAYLIST_TABLE)
data class PlaylistEntity(

    @PrimaryKey
    @ColumnInfo(name = PLAYLIST_ID)
    val id: String,

    @ColumnInfo(name = "name")
    val name: String

)