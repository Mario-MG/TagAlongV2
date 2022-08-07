package com.mariomg.tagalong.di

import com.mariomg.tagalong.auth_interactors_core.session.SessionDataSerializer
import com.mariomg.tagalong.auth_interactors_core.session.SessionManager
import com.mariomg.tagalong.session.SessionDataSerializerImpl
import com.mariomg.tagalong.session.SessionManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Singleton
    @Provides
    fun provideSessionManagerImpl(): SessionManagerImpl {
        return SessionManagerImpl()
    }

    @Singleton
    @Provides
    fun provideSessionManager(sessionManagerImpl: SessionManagerImpl): SessionManager { // TODO: Improve this
        return sessionManagerImpl
    }

    @Singleton
    @Provides
    fun provideSessionDataSerializer(): SessionDataSerializer {
        return SessionDataSerializerImpl()
    }

}