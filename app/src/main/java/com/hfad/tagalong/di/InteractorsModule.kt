package com.hfad.tagalong.di

import android.content.SharedPreferences
import com.hfad.tagalong.cache.dao.*
import com.hfad.tagalong.cache.model.PlaylistEntityMapper
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.interactors.login.GetTokenFromCode
import com.hfad.tagalong.interactors.login.GetTokenFromRefreshToken
import com.hfad.tagalong.interactors.login.LoadSessionInfo
import com.hfad.tagalong.interactors.login.LoadUser
import com.hfad.tagalong.interactors.rulecreation.CreatePlaylist
import com.hfad.tagalong.interactors.rulecreation.CreateRule
import com.hfad.tagalong.interactors.rules.LoadAllRules
import com.hfad.tagalong.interactors.settings.DeleteSessionInfo
import com.hfad.tagalong.interactors.settings.LoadStayLoggedIn
import com.hfad.tagalong.interactors.settings.SaveStayLoggedIn
import com.hfad.tagalong.interactors.singletrack.ApplyExistingRules
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.RetrofitUserService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.model.TokenDtoMapper
import com.hfad.tagalong.network.model.UserDtoMapper
import com.hfad.tagalong.playlist_interactors.AddTracksToPlaylist
import com.hfad.tagalong.playlist_interactors.LoadPlaylistsPage
import com.hfad.tagalong.playlist_interactors_core.repositories.PlaylistNetworkRepository
import com.hfad.tagalong.tag_interactors.FindOrCreateTag
import com.hfad.tagalong.tag_interactors.LoadAllTags
import com.hfad.tagalong.tag_interactors.LoadTagsForTrack
import com.hfad.tagalong.tag_interactors_core.repositories.TagCacheRepository
import com.hfad.tagalong.track_interactors.*
import com.hfad.tagalong.track_interactors_core.repositories.TrackCacheRepository
import com.hfad.tagalong.track_interactors_core.repositories.TrackNetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @Provides
    @ViewModelScoped
    fun provideLoadPlaylistsPage(
        playlistNetworkRepository: PlaylistNetworkRepository,
        @Named("networkErrorMapper") networkErrorMapper: ErrorMapper
    ): LoadPlaylistsPage {
        return LoadPlaylistsPage(
            playlistNetworkRepository = playlistNetworkRepository,
            networkErrorMapper = networkErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadPlaylistTracksPage(
        trackNetworkRepository: TrackNetworkRepository,
        @Named("networkErrorMapper") networkErrorMapper: ErrorMapper
    ): LoadPlaylistTracksPage {
        return LoadPlaylistTracksPage(
            trackNetworkRepository = trackNetworkRepository,
            networkErrorMapper = networkErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadAllTags( // TODO: What if an interactor is shared between several viewmodels??
        tagCacheRepository: TagCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): LoadAllTags {
        return LoadAllTags(
            tagCacheRepository = tagCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadAllTracksForTag(
        trackCacheRepository: TrackCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): LoadAllTracksForTag {
        return LoadAllTracksForTag(
            trackCacheRepository = trackCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadTagsForTrack(
        tagCacheRepository: TagCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): LoadTagsForTrack {
        return LoadTagsForTrack(
            tagCacheRepository = tagCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideFindOrCreateTag(
        tagCacheRepository: TagCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): FindOrCreateTag {
        return FindOrCreateTag(
            tagCacheRepository = tagCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAddTagToTrack(
        trackCacheRepository: TrackCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): AddTagToTrack {
        return AddTagToTrack(
            trackCacheRepository = trackCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideApplyExistingRules(
        ruleDao: RuleDao,
        ruleEntityMapper: RuleEntityMapper,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler,
        playlistService: RetrofitPlaylistService,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler
    ): ApplyExistingRules {
        return ApplyExistingRules(
            ruleDao = ruleDao,
            ruleEntityMapper = ruleEntityMapper,
            cacheErrorHandler = cacheErrorHandler,
            playlistService = playlistService,
            networkErrorHandler = networkErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteTagFromTrack(
        trackCacheRepository: TrackCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): DeleteTagFromTrack {
        return DeleteTagFromTrack(
            trackCacheRepository = trackCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadAllRules(
        ruleDao: RuleDao,
        ruleEntityMapper: RuleEntityMapper,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): LoadAllRules {
        return LoadAllRules(
            ruleDao = ruleDao,
            ruleEntityMapper = ruleEntityMapper,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCreatePlaylist(
        playlistService: RetrofitPlaylistService,
        playlistDtoMapper: PlaylistDtoMapper,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler,
        playlistDao: PlaylistDao,
        playlistEntityMapper: PlaylistEntityMapper,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): CreatePlaylist {
        return CreatePlaylist(
            playlistService = playlistService,
            playlistDtoMapper = playlistDtoMapper,
            networkErrorHandler = networkErrorHandler,
            playlistDao = playlistDao,
            playlistEntityMapper = playlistEntityMapper,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCreateRule(
        ruleDao: RuleDao,
        ruleEntityMapper: RuleEntityMapper,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): CreateRule {
        return CreateRule(
            ruleDao = ruleDao,
            ruleEntityMapper = ruleEntityMapper,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadTracksForRule(
        trackCacheRepository: TrackCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): LoadTracksForRule {
        return LoadTracksForRule(
            trackCacheRepository = trackCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAddTracksToPlaylist(
        playlistNetworkRepository: PlaylistNetworkRepository,
        @Named("networkErrorMapper") networkErrorMapper: ErrorMapper
    ): AddTracksToPlaylist {
        return AddTracksToPlaylist(
            playlistNetworkRepository = playlistNetworkRepository,
            networkErrorMapper = networkErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetTokenFromCode(
        authService: RetrofitAuthService,
        tokenDtoMapper: TokenDtoMapper,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler
    ): GetTokenFromCode {
        return GetTokenFromCode(
            authService = authService,
            tokenDtoMapper = tokenDtoMapper,
            networkErrorHandler = networkErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetTokenFromRefreshToken(
        authService: RetrofitAuthService,
        tokenDtoMapper: TokenDtoMapper,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler
    ): GetTokenFromRefreshToken {
        return GetTokenFromRefreshToken(
            authService = authService,
            tokenDtoMapper = tokenDtoMapper,
            networkErrorHandler = networkErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadUser(
        userService: RetrofitUserService,
        userDtoMapper: UserDtoMapper,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler
    ): LoadUser {
        return LoadUser(
            userService = userService,
            userDtoMapper = userDtoMapper,
            networkErrorHandler = networkErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadSessionInfo(
        @Named("authSharedPreferences") sharedPreferences: SharedPreferences,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): LoadSessionInfo {
        return LoadSessionInfo(
            sharedPreferences = sharedPreferences,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadStayLoggedIn(
        @Named("defaultSharedPreferences") sharedPreferences: SharedPreferences,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): LoadStayLoggedIn {
        return LoadStayLoggedIn(
            sharedPreferences = sharedPreferences,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSaveStayLoggedIn(
        @Named("defaultSharedPreferences") sharedPreferences: SharedPreferences,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): SaveStayLoggedIn {
        return SaveStayLoggedIn(
            sharedPreferences = sharedPreferences,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteSessionInfo(
        @Named("authSharedPreferences") sharedPreferences: SharedPreferences,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): DeleteSessionInfo {
        return DeleteSessionInfo(
            sharedPreferences = sharedPreferences,
            cacheErrorHandler = cacheErrorHandler
        )
    }

}