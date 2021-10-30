package com.hfad.tagalong.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hfad.tagalong.cache.model.TRACK_TAG_CROSS_REF_TABLE
import com.hfad.tagalong.cache.model.TrackTagCrossRef

@Dao
interface TrackTagCrossRefDao {
    @Query("SELECT * FROM $TRACK_TAG_CROSS_REF_TABLE")
    fun getAll(): List<TrackTagCrossRef>

    // TODO: How to find out if there's a conflict when inserting (explore ABORT + @Transaction)
    @Insert
    suspend fun insert(trackTagCrossRef: TrackTagCrossRef)

    @Insert
    suspend fun insertAll(vararg trackTagCrossRefs: TrackTagCrossRef)

    @Delete
    fun delete(trackTagCrossRef: TrackTagCrossRef)
}