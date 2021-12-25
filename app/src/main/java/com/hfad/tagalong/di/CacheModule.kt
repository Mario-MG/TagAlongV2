package com.hfad.tagalong.di

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.hfad.tagalong.cache.dao.*
import com.hfad.tagalong.cache.database.MainDatabase
import com.hfad.tagalong.cache.model.PlaylistEntityMapper
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.presentation.BaseApplication
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

    @Singleton
    @Provides
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

    @Singleton
    @Provides
    fun provideRuleDao(db: MainDatabase): RuleDao {
        return db.ruleDao()
    }

    @Singleton
    @Provides
    fun provideRuleEntityMapper(
        playlistEntityMapper: PlaylistEntityMapper,
        tagEntityMapper: TagEntityMapper
    ): RuleEntityMapper {
        return RuleEntityMapper(
            playlistEntityMapper = playlistEntityMapper,
            tagEntityMapper = tagEntityMapper
        )
    }

    @Singleton
    @Provides
    fun providePlaylistDao(db: MainDatabase): PlaylistDao {
        return db.playlistDao()
    }

    @Singleton
    @Provides
    fun providePlaylistEntityMapper(): PlaylistEntityMapper {
        return PlaylistEntityMapper()
    }

    @Singleton
    @Provides
    @Named("defaultSharedPreferences")
    fun provideDefaultSharedPreferences(
        application: BaseApplication
    ): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Singleton
    @Provides
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

}