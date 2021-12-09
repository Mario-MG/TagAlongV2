package com.hfad.tagalong.di

import com.google.gson.GsonBuilder
import com.hfad.tagalong.BuildConfig
import com.hfad.tagalong.Session
import com.hfad.tagalong.network.*
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.model.TokenDtoMapper
import com.hfad.tagalong.network.model.TrackDtoMapper
import com.hfad.tagalong.network.model.UserDtoMapper
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
            .baseUrl(NETWORK_SPOTIFY_API_BASE_URL)
            .client(retrofitClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RetrofitPlaylistService::class.java)
    }

    @Singleton
    @Provides
    fun providePlaylistMapper(): PlaylistDtoMapper {
        return PlaylistDtoMapper()
    }

    @Singleton
    @Provides
    fun provideTrackService(
        retrofitClient: OkHttpClient
    ): RetrofitTrackService {
        return Retrofit.Builder()
            .baseUrl(NETWORK_SPOTIFY_API_BASE_URL)
            .client(retrofitClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RetrofitTrackService::class.java)
    }

    @Singleton
    @Provides
    fun provideTrackMapper(): TrackDtoMapper {
        return TrackDtoMapper()
    }

    @Singleton
    @Provides
    fun provideUserService(): RetrofitUserService {
        return Retrofit.Builder()
            .baseUrl(NETWORK_SPOTIFY_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(OkHttpClient.Builder().also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
                    client.addInterceptor(logging)
                }
            }.build())
            .build()
            .create(RetrofitUserService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserMapper(): UserDtoMapper {
        return UserDtoMapper()
    }

    @Singleton
    @Provides
    fun provideAuthService(): RetrofitAuthService {
        return Retrofit.Builder()
            .baseUrl(NETWORK_SPOTIFY_AUTH_API_BASE_URL)
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

    @Singleton
    @Provides
    fun provideTokenMapper(): TokenDtoMapper {
        return TokenDtoMapper()
    }

}