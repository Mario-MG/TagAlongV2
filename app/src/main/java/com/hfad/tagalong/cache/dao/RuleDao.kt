package com.hfad.tagalong.cache.dao

import androidx.room.*
import com.hfad.tagalong.cache.model.*

@Dao
abstract class RuleDao {

    @Transaction
    @Query("SELECT * FROM $RULE_TABLE")
    abstract suspend fun getAll(): List<RuleWithTags>

    @Transaction
    @Query("""
        SELECT r.* FROM $RULE_TABLE r
        JOIN $RULE_TAG_CROSS_REF_TABLE rt ON r.$RULE_ID = rt.$RULE_ID
        WHERE r.$RULE_AUTOUPDATE = 1
        AND ((
                r.$RULE_OPTIONALITY = 1
                AND rt.$TAG_ID = :newTagId
                AND rt.$TAG_ID NOT IN (:originalTagsIds) -- TODO: Comprobar que esto funciona
            ) OR (
                r.$RULE_OPTIONALITY = 0
                AND rt.$TAG_ID = :newTagId -- TODO: Comprobar que esto funciona (en caso afirmativo, refactorizar fuera del OR)
                AND rt.$RULE_ID NOT IN (
                    SELECT DISTINCT rt.$RULE_ID FROM $RULE_TAG_CROSS_REF_TABLE rt
                    WHERE rt.$TAG_ID NOT IN (:newTagId, :originalTagsIds)
                )
            )
        )
    """)
    abstract suspend fun getRulesFulfilledByTagIds(newTagId: Long, vararg originalTagsIds: Long): List<RuleWithTags>

    suspend fun insert(ruleWithTags: RuleWithTags) {
        val ruleId = _insert(ruleWithTags.rule)
        _insert(*ruleWithTags.tags.map { tag ->
            RuleTagCrossRef(
                ruleId = ruleId,
                tagId = tag.id
            )
        }.toTypedArray())
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    internal abstract suspend fun _insert(rule: RuleEntity): Long

    @Insert
    internal abstract suspend fun _insert(vararg ruleTagCrossRefs: RuleTagCrossRef)

}