package com.hfad.tagalong.di

import com.google.gson.GsonBuilder
import com.hfad.tagalong.BuildConfig
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.presentation.session.SessionManager
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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthService(): RetrofitAuthService {
        return Retrofit.Builder()
            .baseUrl(NETWORK_SPOTIFY_AUTH_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(
                OkHttpClient.Builder().also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .build()
            .create(RetrofitAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenMapper(): TokenDtoMapper {
        return TokenDtoMapper()
    }

    @Provides
    @Singleton
    fun provideAuthenticator(
        sessionManager: SessionManager,
        authService: RetrofitAuthService,
        tokenDtoMapper: TokenDtoMapper,
        @Named(APP_CLIENT_ID) clientId: String
    ): Authenticator {
        return TokenAuthenticator(
            sessionManager = sessionManager,
            authService = authService,
            tokenDtoMapper = tokenDtoMapper,
            clientId = clientId
        )
    }

    @Provides
    @Singleton
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

    @Provides
    @Singleton
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

    @Provides
    @Singleton
    fun providePlaylistMapper(): PlaylistDtoMapper {
        return PlaylistDtoMapper()
    }

    @Provides
    @Singleton
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

    @Provides
    @Singleton
    fun provideTrackMapper(): TrackDtoMapper {
        return TrackDtoMapper()
    }

    @Provides
    @Singleton
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

    @Provides
    @Singleton
    fun provideUserMapper(): UserDtoMapper {
        return UserDtoMapper()
    }

    @Provides
    @Singleton
    @Named("networkErrorHandler")
    fun provideNetworkErrorHandler(): ErrorHandler {
        return NetworkErrorHandler()
    }

}