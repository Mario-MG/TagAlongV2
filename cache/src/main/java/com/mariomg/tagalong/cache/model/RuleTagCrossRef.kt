package com.mariomg.tagalong.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mariomg.tagalong.cache.*

@Entity(
    tableName = RULE_TAG_CROSS_REF_TABLE,
    primaryKeys = [RULE_ID, TAG_ID],
    foreignKeys = [
        ForeignKey(
            entity = RuleEntity::class,
            parentColumns = [RULE_ID],
            childColumns = [RULE_ID],
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
            value = [RULE_ID]
        ),
        Index(
            value = [TAG_ID]
        )
    ]
)
data class RuleTagCrossRef(

    @ColumnInfo(name = RULE_ID)
    val ruleId: Long,

    @ColumnInfo(name = TAG_ID)
    val tagId: Long

)