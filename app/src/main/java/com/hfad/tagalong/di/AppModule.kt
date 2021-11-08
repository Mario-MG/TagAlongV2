package com.hfad.tagalong.di

import android.content.Context
import com.hfad.tagalong.presentation.BaseApplication
import com.hfad.tagalong.BuildConfig
import com.hfad.tagalong.R
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
    @Named(APP_CLIENT_ID)
    fun provideClientId(): String = BuildConfig.CLIENT_ID // TODO: This is for testing purposes only

    @Singleton
    @Provides
    @Named(APP_REDIRECT_URI)
    fun provideRedirectUri(app: BaseApplication) : String {
        val appSchema = app.resources.getString(R.string.app_deep_link_schema)
        val appUrl = app.resources.getString(R.string.app_deep_link_url)
        return "$appSchema://$appUrl"
    }

}