package com.hfad.tagalong.cache.dao

import androidx.room.*
import com.hfad.tagalong.cache.model.*

@Dao
interface TrackDao {

    @Query("SELECT * FROM $TRACK_TABLE")
    suspend fun getAll(): List<TrackEntity>

    @Query("""
        SELECT DISTINCT tr.* FROM $TRACK_TABLE tr
        JOIN $TRACK_TAG_CROSS_REF_TABLE cr ON tr.$TRACK_ID = cr.$TRACK_ID
        JOIN $TAG_TABLE t ON cr.$TAG_ID = t.$TAG_ID
        WHERE t.$TAG_NAME IN (:tagNames)
    """)
    suspend fun getTracksWithAnyOfTheTagsByName(vararg tagNames: String): List<TrackEntity>

    @Query("""
        SELECT tr.* FROM $TRACK_TABLE tr
        JOIN $TRACK_TAG_CROSS_REF_TABLE cr ON tr.$TRACK_ID = cr.$TRACK_ID
        JOIN $TAG_TABLE t ON cr.$TAG_ID = t.$TAG_ID
        WHERE t.$TAG_NAME IN (:tagNames)
        GROUP BY tr.$TRACK_ID
        HAVING COUNT(*) = JSON_ARRAY_LENGTH(JSON_ARRAY(:tagNames))
    """)
    suspend fun getTracksWithAllOfTheTagsByName(vararg tagNames: String): List<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(track: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg tracks: TrackEntity)

    @Delete(entity = TrackEntity::class)
    suspend fun deleteById(vararg trackIds: TrackEntity.Id): Int

}