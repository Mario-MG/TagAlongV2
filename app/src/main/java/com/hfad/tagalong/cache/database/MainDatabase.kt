package com.hfad.tagalong.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.dao.TrackTagCrossRefDao
import com.hfad.tagalong.cache.model.TagEntity
import com.hfad.tagalong.cache.model.TrackEntity
import com.hfad.tagalong.cache.model.TrackTagCrossRef

@Database(
    entities = [
        TagEntity::class,
        TrackEntity::class,
        TrackTagCrossRef::class
    ],
    version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun tagDao(): TagDao

    abstract fun trackDao(): TrackDao

    abstract fun trackTagCrossRefDao(): TrackTagCrossRefDao

    companion object {
        val DATABASE_NAME: String = "main_db"
    }
}