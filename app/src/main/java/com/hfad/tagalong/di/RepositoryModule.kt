package com.hfad.tagalong.di

import com.hfad.tagalong.cache.dao.*
import com.hfad.tagalong.cache.model.PlaylistEntityMapper
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.RetrofitUserService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.model.TrackDtoMapper
import com.hfad.tagalong.network.model.UserDtoMapper
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePlaylistRepository(
        playlistService: RetrofitPlaylistService,
        playlistDtoMapper: PlaylistDtoMapper,
        playlistDao: PlaylistDao,
        playlistEntityMapper: PlaylistEntityMapper
    ): PlaylistRepository = PlaylistRepositoryImpl(
        playlistService = playlistService,
        playlistDtoMapper = playlistDtoMapper,
        playlistDao = playlistDao,
        playlistEntityMapper = playlistEntityMapper
    )

    @Singleton
    @Provides
    fun provideTrackRepository(
        trackService: RetrofitTrackService,
        trackDtoMapper: TrackDtoMapper,
        trackDao: TrackDao,
        trackEntityMapper: TrackEntityMapper
    ): TrackRepository = TrackRepositoryImpl(
        trackService = trackService,
        trackDtoMapper = trackDtoMapper,
        trackDao = trackDao,
        trackEntityMapper = trackEntityMapper
    )

    @Singleton
    @Provides
    fun provideTagRepository(
        tagDao: TagDao,
        tagEntityMapper: TagEntityMapper
    ): TagRepository = TagRepositoryImpl(
        tagDao = tagDao,
        tagEntityMapper = tagEntityMapper
    )

    @Singleton
    @Provides
    fun provideTrackTagRepository(
        trackDao: TrackDao,
        tagDao: TagDao,
        trackTagCrossRefDao: TrackTagCrossRefDao,
        trackEntityMapper: TrackEntityMapper,
        tagEntityMapper: TagEntityMapper
    ): TrackTagRepository = TrackTagRepositoryImpl(
        trackDao = trackDao,
        tagDao = tagDao,
        trackTagCrossRefDao = trackTagCrossRefDao,
        trackEntityMapper = trackEntityMapper,
        tagEntityMapper = tagEntityMapper
    )

    @Singleton
    @Provides
    fun provideRuleRepository(
        ruleDao: RuleDao,
        ruleEntityMapper: RuleEntityMapper
    ): RuleRepository = RuleRepositoryImpl(
        ruleDao = ruleDao,
        ruleEntityMapper = ruleEntityMapper
    )

    @Singleton
    @Provides
    fun provideUserRepository(
        userService: RetrofitUserService,
        userMapper: UserDtoMapper
    ): UserRepository = UserRepositoryImpl(
        userService = userService,
        userMapper = userMapper
    )

    @Singleton
    @Provides
    fun provideSettingsRepository(
        app: BaseApplication
    ): SettingsRepository = SettingsRepositoryImpl(
        app.applicationContext
    )

}