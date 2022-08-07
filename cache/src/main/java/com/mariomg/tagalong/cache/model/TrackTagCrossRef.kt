package com.mariomg.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mariomg.tagalong.cache.*

@Entity(
    tableName = TRACK_TAG_CROSS_REF_TABLE,
    primaryKeys = [TRACK_ID, TAG_ID],
    foreignKeys = [
        ForeignKey(
            entity = TrackEntity::class,
            parentColumns = [TRACK_ID],
            childColumns = [TRACK_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = [TAG_ID],
            childColumns = [TAG_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = [TRACK_ID]
        ),
        Index(
            value = [TAG_ID]
        )
    ]
)
data class TrackTagCrossRef(

    @ColumnInfo(name = TRACK_ID)
    val trackId: String,

    @ColumnInfo(name = TAG_ID)
    val tagId: Long

)