package com.mariomg.tagalong.di

import androidx.room.Room
import com.mariomg.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.mariomg.tagalong.auth_interactors_core.session.SessionDataSerializer
import com.mariomg.tagalong.cache.CacheErrorMapper
import com.mariomg.tagalong.cache.dao.*
import com.mariomg.tagalong.cache.database.MainDatabase
import com.mariomg.tagalong.cache.model.*
import com.mariomg.tagalong.cache.repositories.*
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.playlist_interactors_core.repositories.PlaylistCacheRepository
import com.mariomg.tagalong.presentation.BaseApplication
import com.mariomg.tagalong.rule_interactors_core.repositories.RuleCacheRepository
import com.mariomg.tagalong.settings_interactors_core.repositories.SettingsRepository
import com.mariomg.tagalong.tag_interactors_core.repositories.TagCacheRepository
import com.mariomg.tagalong.track_interactors_core.repositories.TrackCacheRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideMainDatabase(app: BaseApplication): MainDatabase {
        return Room
            .databaseBuilder(
                app,
                MainDatabase::class.java,
                MainDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration() // TODO: This is for testing purposes only -- TO BE REMOVED
            .openHelperFactory(RequerySQLiteOpenHelperFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideTagDao(db: MainDatabase): TagDao {
        return db.tagDao()
    }

    @Provides
    @Singleton
    fun provideTagEntityMapper(): TagEntityMapper {
        return TagEntityMapper()
    }

    @Provides
    @Singleton
    fun provideTagCacheRepository(
        tagDao: TagDao,
        tagEntityMapper: TagEntityMapper
    ): TagCacheRepository {
        return TagCacheRepositoryImpl(
            tagDao = tagDao,
            tagEntityMapper = tagEntityMapper
        )
    }

    @Provides
    @Singleton
    fun provideTrackDao(db: MainDatabase): TrackDao {
        return db.trackDao()
    }

    @Provides
    @Singleton
    fun provideTrackEntityMapper(): TrackEntityMapper {
        return TrackEntityMapper()
    }

    @Provides
    @Singleton
    fun provideTrackTagCrossRefDao(db: MainDatabase): TrackTagCrossRefDao {
        return db.trackTagCrossRefDao()
    }

    @Provides
    @Singleton
    fun provideTrackTagCrossRefMapper(
        trackEntityMapper: TrackEntityMapper,
        tagEntityMapper: TagEntityMapper
    ): TrackTagCrossRefMapper {
        return TrackTagCrossRefMapper(
            trackEntityMapper = trackEntityMapper,
            tagEntityMapper = tagEntityMapper
        )
    }

    @Provides
    @Singleton
    fun provideTrackCacheRepository(
        trackDao: TrackDao,
        trackEntityMapper: TrackEntityMapper,
        trackTagCrossRefDao: TrackTagCrossRefDao,
        trackTagCrossRefMapper: TrackTagCrossRefMapper
    ): TrackCacheRepository {
        return TrackCacheRepositoryImpl(
            trackDao = trackDao,
            trackEntityMapper = trackEntityMapper,
            trackTagCrossRefDao = trackTagCrossRefDao,
            trackTagCrossRefMapper = trackTagCrossRefMapper
        )
    }

    @Provides
    @Singleton
    fun provideRuleDao(db: MainDatabase): RuleDao {
        return db.ruleDao()
    }

    @Provides
    @Singleton
    fun provideRuleEntityMapper(
        playlistEntityMapper: PlaylistEntityMapper,
        tagEntityMapper: TagEntityMapper
    ): RuleEntityMapper {
        return RuleEntityMapper(
            playlistEntityMapper = playlistEntityMapper,
            tagEntityMapper = tagEntityMapper
        )
    }

    @Provides
    @Singleton
    fun provideRuleCacheRepository(
        ruleDao: RuleDao,
        ruleEntityMapper: RuleEntityMapper
    ): RuleCacheRepository {
        return RuleCacheRepositoryImpl(
            ruleDao = ruleDao,
            ruleEntityMapper = ruleEntityMapper
        )
    }

    @Provides
    @Singleton
    fun providePlaylistDao(db: MainDatabase): PlaylistDao {
        return db.playlistDao()
    }

    @Provides
    @Singleton
    fun providePlaylistEntityMapper(): PlaylistEntityMapper {
        return PlaylistEntityMapper()
    }

    @Provides
    @Singleton
    fun providePlaylistCacheRepository(
        playlistDao: PlaylistDao,
        playlistEntityMapper: PlaylistEntityMapper
    ): PlaylistCacheRepository {
        return PlaylistCacheRepositoryImpl(
            playlistDao = playlistDao,
            playlistEntityMapper = playlistEntityMapper
        )
    }

    @Provides
    @Singleton
    fun provideAuthCacheRepository(
        application: BaseApplication,
        sessionDataSerializer: SessionDataSerializer
    ): AuthCacheRepository {
        return AuthCacheRepositoryImpl(
            context = application,
            sessionDataSerializer = sessionDataSerializer
        )
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        application: BaseApplication
    ): SettingsRepository {
        return SettingsRepositoryImpl(
            context = application
        )
    }

    @Provides
    @Singleton
    @Named("cacheErrorMapper")
    fun provideCacheErrorMapper(): ErrorMapper {
        return CacheErrorMapper()
    }

}