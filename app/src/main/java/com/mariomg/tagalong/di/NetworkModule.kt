package com.mariomg.tagalong.di

import com.google.gson.GsonBuilder
import com.mariomg.tagalong.BuildConfig
import com.mariomg.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.mariomg.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.mariomg.tagalong.auth_interactors_core.session.SessionManager
import com.mariomg.tagalong.interactors_core.data.ErrorMapper
import com.mariomg.tagalong.playlist_network.model.PlaylistDtoMapper
import com.mariomg.tagalong.auth_network.model.TokenDtoMapper
import com.mariomg.tagalong.track_network.model.TrackDtoMapper
import com.mariomg.tagalong.auth_network.model.UserDtoMapper
import com.mariomg.tagalong.auth_network.services.RetrofitAuthService
import com.mariomg.tagalong.auth_network.services.RetrofitUserService
import com.mariomg.tagalong.network_core.util.NetworkErrorMapper
import com.mariomg.tagalong.auth_network.repositories.AuthNetworkRepositoryImpl
import com.mariomg.tagalong.playlist_network.repositories.PlaylistNetworkRepositoryImpl
import com.mariomg.tagalong.track_network.repositories.TrackNetworkRepositoryImpl
import com.mariomg.tagalong.session.SessionManagerImpl
import com.mariomg.tagalong.network_core.util.AuthManager
import com.mariomg.tagalong.network_core.util.TokenAuthenticator
import com.mariomg.tagalong.network_core.util.UserManager
import com.mariomg.tagalong.playlist_interactors_core.repositories.PlaylistNetworkRepository
import com.mariomg.tagalong.playlist_network.services.RetrofitPlaylistService
import com.mariomg.tagalong.settings_interactors_core.repositories.SettingsRepository
import com.mariomg.tagalong.track_interactors_core.repositories.TrackNetworkRepository
import com.mariomg.tagalong.track_network.services.RetrofitTrackService
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
//                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
//                    }
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
        authNetworkRepository: AuthNetworkRepository,
        authManager: AuthManager,
        authCacheRepository: AuthCacheRepository,
        settingsRepository: SettingsRepository,
        sessionManager: SessionManager
    ): Authenticator {
        return TokenAuthenticator(
            authNetworkRepository = authNetworkRepository,
            authManager = authManager,
            authCacheRepository = authCacheRepository,
            settingsRepository = settingsRepository,
            sessionManager = sessionManager
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
//                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
//                }
            }.build()
    }

    @Provides
    @Singleton
    fun provideAuthManager(
        sessionManager: SessionManagerImpl
    ): AuthManager {
        return sessionManager
    }

    @Provides
    @Singleton
    fun provideUserManager(
        sessionManager: SessionManagerImpl
    ): UserManager {
        return sessionManager
    }

    @Provides
    @Singleton
    fun provideAuthNetworkRepository(
        authService: RetrofitAuthService,
        tokenMapper: TokenDtoMapper,
        userService: RetrofitUserService,
        userMapper: UserDtoMapper,
        @Named(APP_CLIENT_ID) clientId: String,
        @Named(APP_REDIRECT_URI) redirectUri: String
    ): AuthNetworkRepository {
        return AuthNetworkRepositoryImpl(
            authService = authService,
            tokenMapper = tokenMapper,
            userService = userService,
            userMapper = userMapper,
            clientId = clientId,
            redirectUri = redirectUri
        )
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
    fun providePlaylistNetworkRepository(
        playlistService: RetrofitPlaylistService,
        playlistDtoMapper: PlaylistDtoMapper,
        authManager: AuthManager,
        userManager: UserManager
    ): PlaylistNetworkRepository {
        return PlaylistNetworkRepositoryImpl(
            playlistService = playlistService,
            playlistDtoMapper = playlistDtoMapper,
            authManager = authManager,
            userManager = userManager
        )
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
    fun provideTrackNetworkRepository(
        trackService: RetrofitTrackService,
        trackDtoMapper: TrackDtoMapper,
        authManager: AuthManager
    ): TrackNetworkRepository {
        return TrackNetworkRepositoryImpl(
            trackService = trackService,
            trackDtoMapper = trackDtoMapper,
            authManager = authManager
        )
    }

    @Provides
    @Singleton
    fun provideUserService(): RetrofitUserService {
        return Retrofit.Builder()
            .baseUrl(NETWORK_SPOTIFY_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(OkHttpClient.Builder().also { client ->
//                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
//                }
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
    @Named("networkErrorMapper")
    fun provideNetworkErrorMapper(): ErrorMapper {
        return NetworkErrorMapper()
    }

}