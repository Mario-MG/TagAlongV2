package com.hfad.tagalong.di

import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.model.TrackDtoMapper
import com.hfad.tagalong.repository.PlaylistRepository
import com.hfad.tagalong.repository.PlaylistRepositoryImpl
import com.hfad.tagalong.repository.TrackRepository
import com.hfad.tagalong.repository.TrackRepositoryImpl
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
        trackMapper: TrackDtoMapper
    ): TrackRepository = TrackRepositoryImpl(
        trackService = trackService,
        trackMapper = trackMapper
    )

}