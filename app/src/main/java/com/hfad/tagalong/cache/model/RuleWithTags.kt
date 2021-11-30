package com.hfad.tagalong.cache.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RuleWithTags(

    @Embedded
    val rule: RuleEntity,
    
    @Relation(
        parentColumn = RULE_ID,
        entityColumn = TAG_ID,
        associateBy = Junction(
            RuleTagCrossRef::class,
            parentColumn = RULE_ID,
            entityColumn = TAG_ID
        )
    )
    val tags: List<TagEntity>
    
)