package com.hfad.tagalong.cache.dao

import androidx.room.*
import com.hfad.tagalong.cache.model.*

@Dao
interface TagDao {

    @Query("""
        SELECT t.$TAG_ID, t.$TAG_NAME, COUNT(cr.$TAG_ID) as $TAG_SIZE FROM $TAG_TABLE t
        LEFT JOIN $TRACK_TAG_CROSS_REF_TABLE cr ON t.$TAG_ID = cr.$TAG_ID
        GROUP BY t.$TAG_ID, t.$TAG_NAME
    """)
    suspend fun getAll(): List<TagEntity>

    @Query("""
        SELECT * FROM $TAG_TABLE
        WHERE $TAG_ID IN (
            SELECT $TAG_ID FROM $TRACK_TAG_CROSS_REF_TABLE
            WHERE $TRACK_ID = :trackId
        )
    """)
    suspend fun getTagsForTrackById(trackId: String): List<TagEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: TagEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg tags: TagEntity): List<Long>

    @Delete
    suspend fun delete(vararg tag: TagEntity): Int

}