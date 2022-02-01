package com.hfad.tagalong.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.hfad.tagalong.cache.CacheErrorHandler
import com.hfad.tagalong.cache.CacheErrorMapper
import com.hfad.tagalong.cache.dao.*
import com.hfad.tagalong.cache.database.MainDatabase
import com.hfad.tagalong.cache.model.*
import com.hfad.tagalong.cache.repositories.PlaylistCacheRepositoryImpl
import com.hfad.tagalong.cache.repositories.RuleCacheRepositoryImpl
import com.hfad.tagalong.cache.repositories.TagCacheRepositoryImpl
import com.hfad.tagalong.cache.repositories.TrackCacheRepositoryImpl
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.playlist_interactors_core.repositories.PlaylistCacheRepository
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.rule_interactors_core.repositories.RuleCacheRepository
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
    fun provideTrackTagCrossRefMapper(): TrackTagCrossRefMapper {
        return TrackTagCrossRefMapper()
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
    @Named("defaultSharedPreferences")
    fun provideDefaultSharedPreferences(
        application: BaseApplication
    ): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    @Named("authSharedPreferences")
    fun provideAuthSharedPreferences(
        application: BaseApplication
    ): SharedPreferences {
        val masterKey = MasterKey.Builder(application, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            application,
            AUTH_SHARED_PREFS,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    @Named("cacheErrorHandler")
    fun provideCacheErrorHandler(): ErrorHandler {
        return CacheErrorHandler()
    }

    @Provides
    @Singleton
    @Named("cacheErrorMapper")
    fun provideCacheErrorMapper(): ErrorMapper {
        return CacheErrorMapper()
    }

}