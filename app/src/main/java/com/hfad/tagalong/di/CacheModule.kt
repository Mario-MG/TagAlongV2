package com.hfad.tagalong.di

import androidx.room.Room
import com.hfad.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.hfad.tagalong.auth_interactors_core.session.SessionDataSerializer
import com.hfad.tagalong.cache.CacheErrorMapper
import com.hfad.tagalong.cache.dao.*
import com.hfad.tagalong.cache.database.MainDatabase
import com.hfad.tagalong.cache.model.*
import com.hfad.tagalong.cache.repositories.*
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.playlist_interactors_core.repositories.PlaylistCacheRepository
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.rule_interactors_core.repositories.RuleCacheRepository
import com.hfad.tagalong.settings_interactors_core.repositories.SettingsRepository
import com.hfad.tagalong.tag_interactors_core.repositories.TagCacheRepository
import com.hfad.tagalong.track_interactors_core.repositories.TrackCacheRepository
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
            application = application,
            sessionDataSerializer = sessionDataSerializer
        )
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        application: BaseApplication
    ): SettingsRepository {
        return SettingsRepositoryImpl(
            application = application
        )
    }

    @Provides
    @Singleton
    @Named("cacheErrorMapper")
    fun provideCacheErrorMapper(): ErrorMapper {
        return CacheErrorMapper()
    }

}