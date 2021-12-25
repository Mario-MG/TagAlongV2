package com.hfad.tagalong.cache.dao

import androidx.room.*
import com.hfad.tagalong.cache.model.*

@Dao
abstract class TagDao {

    @Query("""
        SELECT t.$TAG_ID, t.$TAG_NAME, COUNT(cr.$TAG_ID) as $TAG_SIZE FROM $TAG_TABLE t
        LEFT JOIN $TRACK_TAG_CROSS_REF_TABLE cr ON t.$TAG_ID = cr.$TAG_ID
        GROUP BY t.$TAG_ID, t.$TAG_NAME
    """)
    abstract suspend fun getAll(): List<TagEntityPoko>

    @Query("""
        SELECT t.$TAG_ID, t.$TAG_NAME, COUNT(cr.$TAG_ID) as $TAG_SIZE FROM $TAG_TABLE t
        LEFT JOIN $TRACK_TAG_CROSS_REF_TABLE cr ON t.$TAG_ID = cr.$TAG_ID
        WHERE t.$TAG_ID = :tagId
        GROUP BY t.$TAG_ID, t.$TAG_NAME
    """)
    abstract suspend fun getById(tagId: Long): TagEntityPoko

    @Query("""
        SELECT t.$TAG_ID, t.$TAG_NAME, COUNT(cr.$TAG_ID) as $TAG_SIZE FROM $TAG_TABLE t
        LEFT JOIN $TRACK_TAG_CROSS_REF_TABLE cr ON t.$TAG_ID = cr.$TAG_ID
        WHERE cr.$TRACK_ID = :trackId
        GROUP BY t.$TAG_ID, t.$TAG_NAME
    """)
    abstract suspend fun getTagsForTrackById(trackId: String): List<TagEntityPoko>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(tag: TagEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(vararg tags: TagEntity): List<Long>

    @Delete
    abstract suspend fun delete(vararg tags: TagEntity): Int

    suspend fun insert(tag: TagEntityPoko): Long {
        return insert(tag.tagEntity)
    }

    suspend fun insert(vararg tags: TagEntityPoko): List<Long> {
        return insert(*tags.map(TagEntityPoko::tagEntity).toTypedArray())
    }

    suspend fun delete(vararg tags: TagEntityPoko): Int {
        return delete(*tags.map(TagEntityPoko::tagEntity).toTypedArray())
    }

}