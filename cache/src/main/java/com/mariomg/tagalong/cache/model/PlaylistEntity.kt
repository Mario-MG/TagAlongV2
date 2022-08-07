package com.mariomg.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mariomg.tagalong.cache.*

@Entity(tableName = PLAYLIST_TABLE)
data class PlaylistEntity(

    @PrimaryKey
    @ColumnInfo(name = PLAYLIST_ID)
    val id: String,

    @ColumnInfo(name = "name")
    val name: String

)