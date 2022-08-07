package com.mariomg.tagalong.cache.dao

import androidx.room.*
import com.mariomg.tagalong.cache.*
import com.mariomg.tagalong.cache.model.TrackEntity

@Dao
interface TrackDao {

    @Query("SELECT * FROM $TRACK_TABLE")
    suspend fun getAll(): List<TrackEntity>

    @Query("""
        SELECT DISTINCT tr.* FROM $TRACK_TABLE tr
        JOIN $TRACK_TAG_CROSS_REF_TABLE cr ON tr.$TRACK_ID = cr.$TRACK_ID
        WHERE cr.$TAG_ID IN (:tagIds)
    """)
    suspend fun getTracksWithAnyOfTheTagsById(vararg tagIds: Long): List<TrackEntity>

    @Query("""
        SELECT tr.* FROM $TRACK_TABLE tr
        JOIN $TRACK_TAG_CROSS_REF_TABLE cr ON tr.$TRACK_ID = cr.$TRACK_ID
        WHERE cr.$TAG_ID IN (:tagIds)
        GROUP BY tr.$TRACK_ID
        HAVING COUNT(*) = JSON_ARRAY_LENGTH(JSON_ARRAY(:tagIds))
    """)
    suspend fun getTracksWithAllOfTheTagsById(vararg tagIds: Long): List<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(track: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg tracks: TrackEntity)

    @Delete
    suspend fun delete(vararg tracks: TrackEntity): Int

}