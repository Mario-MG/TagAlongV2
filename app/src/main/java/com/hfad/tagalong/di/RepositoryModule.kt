package com.hfad.tagalong.di

import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.repository.PlaylistRepository
import com.hfad.tagalong.repository.PlaylistRepositoryImpl
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
        playlistService: RetrofitPlaylistService // TODO: Should this dependency be an abstraction?
        // TODO: Add mapper here
    ): PlaylistRepository = PlaylistRepositoryImpl(
        playlistService = playlistService
    )

}