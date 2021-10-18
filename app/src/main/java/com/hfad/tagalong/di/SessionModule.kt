package com.hfad.tagalong.di

import com.hfad.tagalong.BuildConfig
import com.hfad.tagalong.Session
import com.hfad.tagalong.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Singleton
    @Provides
    @Named("refresh_token")
    fun provideRefreshToken(): String = BuildConfig.REFRESH_TOKEN // TODO: This is for testing purposes only

    @Singleton
    @Provides
    fun provideSession(
        authRepository: AuthRepository,
        @Named("refresh_token") refreshToken: String,
        @Named("client_id") clientId: String
    ): Session {
        return Session(
            authRepository = authRepository,
            refreshToken = refreshToken,
            clientId = clientId
        )
    }

}