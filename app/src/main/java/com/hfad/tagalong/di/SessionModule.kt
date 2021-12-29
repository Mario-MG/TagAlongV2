package com.hfad.tagalong.di

import android.content.SharedPreferences
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.interactors.login.SaveSessionInfo
import com.hfad.tagalong.presentation.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideSaveSessionInfo(
        @Named("authSharedPreferences") sharedPreferences: SharedPreferences,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): SaveSessionInfo {
        return SaveSessionInfo(
            sharedPreferences = sharedPreferences,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Singleton
    @Provides
    fun provideSessionManager(
        saveSessionInfo: SaveSessionInfo
    ): SessionManager {
        return SessionManager(
            saveSessionInfo = saveSessionInfo
        )
    }

}