package com.hfad.tagalong.di

import androidx.room.Room
import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.dao.TrackTagCrossRefDao
import com.hfad.tagalong.cache.database.MainDatabase
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideMainDatabase(app: BaseApplication): MainDatabase {
        return Room
            .databaseBuilder(
                app,
                MainDatabase::class.java,
                MainDatabase.DATABASE_NAME
            )
            .openHelperFactory(RequerySQLiteOpenHelperFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideTagDao(db: MainDatabase): TagDao {
        return db.tagDao()
    }

    @Singleton
    @Provides
    fun provideTagEntityMapper(): TagEntityMapper {
        return TagEntityMapper()
    }

    @Singleton
    @Provides
    fun provideTrackDao(db: MainDatabase): TrackDao {
        return db.trackDao()
    }

    @Singleton
    @Provides
    fun provideTrackEntityMapper(): TrackEntityMapper {
        return TrackEntityMapper()
    }

    @Singleton
    @Provides
    fun provideTrackTagCrossRefDao(db: MainDatabase): TrackTagCrossRefDao {
        return db.trackTagCrossRefDao()
    }

}