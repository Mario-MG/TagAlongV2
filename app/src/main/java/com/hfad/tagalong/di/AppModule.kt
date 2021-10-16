package com.hfad.tagalong.di

import android.content.Context
import com.hfad.tagalong.BaseApplication
import com.hfad.tagalong.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    @Named("client_id")
    fun provideClientId(): String = BuildConfig.CLIENT_ID // TODO: This is for testing purposes only

}