package com.hfad.tagalong.di

import com.hfad.tagalong.presentation.session.SessionManager
import com.hfad.tagalong.repository.AuthRepository
import com.hfad.tagalong.repository.UserRepository
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
        @Named(APP_CLIENT_ID) clientId: String,
        userRepository: UserRepository
    ): SessionManager {
        return SessionManager(
            authRepository = authRepository,
            clientId = clientId,
            userRepository = userRepository
        )
    }

}