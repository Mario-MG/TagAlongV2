package com.mariomg.tagalong.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mariomg.tagalong.cache.dao.*
import com.mariomg.tagalong.cache.model.*

@Database(
    entities = [
        TagEntity::class,
        TrackEntity::class,
        TrackTagCrossRef::class,
        RuleEntity::class,
        RuleTagCrossRef::class,
        PlaylistEntity::class,
        RulePlaylistCrossRef::class
    ],
    version = 1
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun tagDao(): TagDao

    abstract fun trackDao(): TrackDao

    abstract fun trackTagCrossRefDao(): TrackTagCrossRefDao

    abstract fun ruleDao(): RuleDao

    abstract fun playlistDao(): PlaylistDao

    companion object {
        val DATABASE_NAME: String = "main_db"
    }
}