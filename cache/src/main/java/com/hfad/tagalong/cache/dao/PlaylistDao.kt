package com.hfad.tagalong.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import com.hfad.tagalong.cache.model.PlaylistEntity

@Dao
interface PlaylistDao {

    @Insert
    suspend fun insert(vararg playlist: PlaylistEntity)

}