package com.hfad.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

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
    ]
)
data class TrackTagCrossRef (

    @ColumnInfo(name = TRACK_ID)
    val trackId: String,
    
    @ColumnInfo(name = TAG_ID)
    val tagId: Long
    
)