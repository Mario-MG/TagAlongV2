package com.hfad.tagalong.di

import com.hfad.tagalong.network.session.SessionDataSerializerImpl
import com.hfad.tagalong.network.session.SessionManagerImpl
import com.hfad.tagalong.session.SessionDataSerializer
import com.hfad.tagalong.session.SessionManager
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