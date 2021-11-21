package com.hfad.tagalong.di

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.dao.TrackTagCrossRefDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.model.TrackDtoMapper
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
        playlistService: RetrofitPlaylistService, // TODO: Should this dependency be an abstraction?
        playlistMapper: PlaylistDtoMapper
    ): PlaylistRepository = PlaylistRepositoryImpl(
        playlistService = playlistService,
        playlistMapper = playlistMapper
    )

    @Singleton
    @Provides
    fun provideTrackRepository(
        trackService: RetrofitTrackService, // TODO: Should this dependency be an abstraction?
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

}