package com.hfad.tagalong.di

import com.google.gson.GsonBuilder
import com.hfad.tagalong.BuildConfig
import com.hfad.tagalong.Session
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAuthenticator(
        session: Session
    ): Authenticator {
        return TokenAuthenticator(session = session)
    }

    @Singleton
    @Provides
    // Source: https://www.simplifiedcoding.net/retrofit-authenticator-refresh-token/
    fun provideRefrofitClient(
        authenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient.Builder().also { client ->
                client.authenticator(authenticator)
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
                    client.addInterceptor(logging)
                }
            }.build()
    }

    @Singleton
    @Provides
    fun providePlaylistService(
        retrofitClient: OkHttpClient
    ): RetrofitPlaylistService {
        return Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/") // TODO: Make constant
            .client(retrofitClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RetrofitPlaylistService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthService(): RetrofitAuthService {
        return Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/api/") // TODO: Make constant
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(OkHttpClient.Builder().also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
                        client.addInterceptor(logging)
                    }
                }.build())
            .build()
            .create(RetrofitAuthService::class.java)
    }

}