package com.hfad.tagalong.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hfad.tagalong.cache.model.RULE_TAG_CROSS_REF_TABLE
import com.hfad.tagalong.cache.model.RuleTagCrossRef

@Dao
interface RuleTagCrossRefDao {

    @Query("SELECT * FROM $RULE_TAG_CROSS_REF_TABLE")
    suspend fun getAll(): List<RuleTagCrossRef>

    @Insert
    suspend fun insert(ruleTagCrossRef: RuleTagCrossRef)

    @Insert
    suspend fun insert(vararg ruleTagCrossRefs: RuleTagCrossRef)

    @Delete
    suspend fun delete(ruleTagCrossRef: RuleTagCrossRef): Int
    
}