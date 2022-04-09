package com.hfad.tagalong.cache.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.hfad.tagalong.cache.*

data class RuleEntityPoko(

    @Embedded
    val rule: RuleEntity,

    @Relation(
        parentColumn = RULE_ID,
        entityColumn = PLAYLIST_ID,
        associateBy = Junction(
            RulePlaylistCrossRef::class,
            parentColumn = RULE_ID,
            entityColumn = PLAYLIST_ID
        )
    )
    val playlist: PlaylistEntity,
    
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