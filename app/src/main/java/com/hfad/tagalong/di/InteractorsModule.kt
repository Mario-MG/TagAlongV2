package com.hfad.tagalong.di

import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.interactors.playlists.LoadFirstPlaylistsPage
import com.hfad.tagalong.interactors.playlists.LoadNextPlaylistsPage
import com.hfad.tagalong.interactors.playlisttracks.LoadFirstPlaylistTracksPage
import com.hfad.tagalong.interactors.playlisttracks.LoadNextPlaylistTracksPage
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.model.TrackDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @Provides
    @ViewModelScoped
    fun provideLoadFirstPlaylistsPage(
        playlistService: RetrofitPlaylistService,
        playlistDtoMapper: PlaylistDtoMapper
    ): LoadFirstPlaylistsPage {
        return LoadFirstPlaylistsPage(
            playlistService = playlistService,
            playlistDtoMapper = playlistDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadNextPlaylistsPage(
        playlistService: RetrofitPlaylistService,
        playlistDtoMapper: PlaylistDtoMapper
    ): LoadNextPlaylistsPage {
        return LoadNextPlaylistsPage(
            playlistService = playlistService,
            playlistDtoMapper = playlistDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadFirstPlaylistTracksPage(
        trackService: RetrofitTrackService,
        trackDtoMapper: TrackDtoMapper
    ): LoadFirstPlaylistTracksPage {
        return LoadFirstPlaylistTracksPage(
            trackService = trackService,
            trackDtoMapper = trackDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadNextPlaylistTracksPage(
        trackService: RetrofitTrackService,
        trackDtoMapper: TrackDtoMapper
    ): LoadNextPlaylistTracksPage {
        return LoadNextPlaylistTracksPage(
            trackService = trackService,
            trackDtoMapper = trackDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadAllTags(
        tagDao: TagDao,
        tagEntityMapper: TagEntityMapper
    ): LoadAllTags {
        return LoadAllTags(
            tagDao = tagDao,
            tagEntityMapper = tagEntityMapper
        )
    }

}