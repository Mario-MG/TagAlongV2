package com.hfad.tagalong.di

import com.hfad.tagalong.interactors.playlists.LoadFirstPlaylistsPage
import com.hfad.tagalong.interactors.playlists.LoadNextPlaylistsPage
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
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

}