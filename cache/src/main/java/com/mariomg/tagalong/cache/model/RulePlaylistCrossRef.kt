package com.mariomg.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mariomg.tagalong.cache.*

@Entity(
    tableName = RULE_PLAYLIST_CROSS_REF_TABLE,
    primaryKeys = [RULE_ID, PLAYLIST_ID],
    foreignKeys = [
        ForeignKey(
            entity = RuleEntity::class,
            parentColumns = [RULE_ID],
            childColumns = [RULE_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlaylistEntity::class,
            parentColumns = [PLAYLIST_ID],
            childColumns = [PLAYLIST_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = [RULE_ID]
        ),
        Index(
            value = [PLAYLIST_ID]
        )
    ]
)
data class RulePlaylistCrossRef(

    @ColumnInfo(name = RULE_ID)
    val ruleId: Long,

    @ColumnInfo(name = PLAYLIST_ID)
    val playlistId: String

)