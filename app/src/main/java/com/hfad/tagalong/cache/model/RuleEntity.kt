package com.hfad.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RULE_TABLE)
data class RuleEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = RULE_ID)
    val id: Long,

    @ColumnInfo(name = RULE_PLAYLIST_ID)
    val playlistId: String,

    @ColumnInfo(name = RULE_OPTIONALITY)
    val optionality: Boolean,

    @ColumnInfo(name = RULE_AUTOUPDATE)
    val autoUpdate: Boolean

)