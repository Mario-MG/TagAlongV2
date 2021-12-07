package com.hfad.tagalong.cache.dao

import androidx.room.*
import com.hfad.tagalong.cache.model.*

@Dao
abstract class RuleDao {

    @Transaction
    @Query("SELECT * FROM $RULE_TABLE")
    abstract suspend fun getAll(): List<RulePoko>

    suspend fun getRulesFulfilledByTagIds(newTagId: Long, vararg originalTagsIds: Long): List<RulePoko> {
        return if (originalTagsIds.isNotEmpty()) {
            _getRulesFulfilledByTagIds(newTagId = newTagId, originalTagsIds = originalTagsIds)
        } else {
            _getRulesFulfilledByTagIds(newTagId = newTagId)
        }
    }

    @Transaction
    @Query("""
        SELECT r.* FROM $RULE_TABLE r
        JOIN $RULE_TAG_CROSS_REF_TABLE rt ON r.$RULE_ID = rt.$RULE_ID
        WHERE r.$RULE_AUTOUPDATE = 1
                AND rt.$TAG_ID = :newTagId
        AND ((
                r.$RULE_OPTIONALITY = 1
                AND rt.$TAG_ID NOT IN (:originalTagsIds)
            ) OR (
                r.$RULE_OPTIONALITY = 0
                AND rt.$RULE_ID NOT IN (
                    SELECT DISTINCT rt.$RULE_ID FROM $RULE_TAG_CROSS_REF_TABLE rt
                    WHERE rt.$TAG_ID NOT IN (:newTagId, :originalTagsIds)
                )
            )
        )
    """)
    internal abstract suspend fun _getRulesFulfilledByTagIds(newTagId: Long, vararg originalTagsIds: Long): List<RulePoko>

    @Transaction
    @Query("""
        SELECT r.* FROM $RULE_TABLE r
        JOIN $RULE_TAG_CROSS_REF_TABLE rt ON r.$RULE_ID = rt.$RULE_ID
        WHERE r.$RULE_AUTOUPDATE = 1
                AND rt.$TAG_ID = :newTagId
        AND ((
                r.$RULE_OPTIONALITY = 1
            ) OR (
                r.$RULE_OPTIONALITY = 0
                AND rt.$RULE_ID NOT IN (
                    SELECT DISTINCT rt.$RULE_ID FROM $RULE_TAG_CROSS_REF_TABLE rt
                    WHERE rt.$TAG_ID NOT IN (:newTagId)
                )
            )
        )
    """)
    internal abstract suspend fun _getRulesFulfilledByTagIds(newTagId: Long): List<RulePoko>

    suspend fun insert(rulePoko: RulePoko) {
        val ruleId = _insert(rulePoko.rule)
        _insert(
            RulePlaylistCrossRef(
                ruleId = ruleId,
                playlistId = rulePoko.playlist.id
            )
        )
        _insert(*rulePoko.tags.map { tag ->
            RuleTagCrossRef(
                ruleId = ruleId,
                tagId = tag.id
            )
        }.toTypedArray())
    }

    @Insert
    internal abstract suspend fun _insert(rule: RuleEntity): Long

    @Insert
    internal abstract suspend fun _insert(vararg rulePlaylistCrossRefs: RulePlaylistCrossRef)

    @Insert
    internal abstract suspend fun _insert(vararg ruleTagCrossRefs: RuleTagCrossRef)

}