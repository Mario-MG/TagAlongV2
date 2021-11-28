package com.hfad.tagalong.di

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
    fun provideSession(
        authRepository: AuthRepository,
        @Named(APP_CLIENT_ID) clientId: String
    ): Session {
        return Session(
            authRepository = authRepository,
            clientId = clientId
        )
    }

}