package com.hfad.tagalong.di

import android.content.SharedPreferences
import com.hfad.tagalong.cache.dao.*
import com.hfad.tagalong.cache.model.PlaylistEntityMapper
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.interactors.login.GetTokenFromCode
import com.hfad.tagalong.interactors.login.GetTokenFromRefreshToken
import com.hfad.tagalong.interactors.login.LoadSessionInfo
import com.hfad.tagalong.interactors.login.LoadUser
import com.hfad.tagalong.interactors.playlists.LoadFirstPlaylistsPage
import com.hfad.tagalong.interactors.playlists.LoadNextPlaylistsPage
import com.hfad.tagalong.interactors.playlisttracks.LoadFirstPlaylistTracksPage
import com.hfad.tagalong.interactors.playlisttracks.LoadNextPlaylistTracksPage
import com.hfad.tagalong.interactors.rulecreation.ApplyNewRule
import com.hfad.tagalong.interactors.rulecreation.CreatePlaylist
import com.hfad.tagalong.interactors.rulecreation.CreateRule
import com.hfad.tagalong.interactors.rules.LoadAllRules
import com.hfad.tagalong.interactors.settings.DeleteSessionInfo
import com.hfad.tagalong.interactors.settings.LoadStayLoggedIn
import com.hfad.tagalong.interactors.settings.SaveStayLoggedIn
import com.hfad.tagalong.interactors.singletrack.*
import com.hfad.tagalong.interactors.tagtracks.LoadAllTagTracks
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.RetrofitUserService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.model.TokenDtoMapper
import com.hfad.tagalong.network.model.TrackDtoMapper
import com.hfad.tagalong.network.model.UserDtoMapper
import com.hfad.tagalong.tag_interactors.LoadAllTags
import com.hfad.tagalong.tag_interactors_core.repositories.TagCacheRepository
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
    fun provideLoadFirstPlaylistsPage(
        playlistService: RetrofitPlaylistService,
        playlistDtoMapper: PlaylistDtoMapper,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler
    ): LoadFirstPlaylistsPage {
        return LoadFirstPlaylistsPage(
            playlistService = playlistService,
            playlistDtoMapper = playlistDtoMapper,
            networkErrorHandler = networkErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadNextPlaylistsPage(
        playlistService: RetrofitPlaylistService,
        playlistDtoMapper: PlaylistDtoMapper,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler
    ): LoadNextPlaylistsPage {
        return LoadNextPlaylistsPage(
            playlistService = playlistService,
            playlistDtoMapper = playlistDtoMapper,
            networkErrorHandler = networkErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadFirstPlaylistTracksPage(
        trackService: RetrofitTrackService,
        trackDtoMapper: TrackDtoMapper,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler
    ): LoadFirstPlaylistTracksPage {
        return LoadFirstPlaylistTracksPage(
            trackService = trackService,
            trackDtoMapper = trackDtoMapper,
            networkErrorHandler = networkErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadNextPlaylistTracksPage(
        trackService: RetrofitTrackService,
        trackDtoMapper: TrackDtoMapper,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler
    ): LoadNextPlaylistTracksPage {
        return LoadNextPlaylistTracksPage(
            trackService = trackService,
            trackDtoMapper = trackDtoMapper,
            networkErrorHandler = networkErrorHandler
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
    fun provideLoadAllTagTracks(
        trackDao: TrackDao,
        trackEntityMapper: TrackEntityMapper,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): LoadAllTagTracks {
        return LoadAllTagTracks(
            trackDao = trackDao,
            trackEntityMapper = trackEntityMapper,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadTrackTags(
        tagDao: TagDao,
        tagEntityMapper: TagEntityMapper,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): LoadTrackTags {
        return LoadTrackTags(
            tagDao = tagDao,
            tagEntityMapper = tagEntityMapper,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCreateTag(
        tagDao: TagDao,
        tagEntityMapper: TagEntityMapper,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): CreateTag {
        return CreateTag(
            tagDao = tagDao,
            tagEntityMapper = tagEntityMapper,
            cacheErrorHandler = cacheErrorHandler
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAddTagToTrack(
        trackDao: TrackDao,
        trackEntityMapper: TrackEntityMapper,
        trackTagCrossRefDao: TrackTagCrossRefDao,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): AddTagToTrack {
        return AddTagToTrack(
            trackDao = trackDao,
            trackEntityMapper = trackEntityMapper,
            cacheErrorHandler = cacheErrorHandler,
            trackTagCrossRefDao = trackTagCrossRefDao,
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
        trackTagCrossRefDao: TrackTagCrossRefDao,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler
    ): DeleteTagFromTrack {
        return DeleteTagFromTrack(
            trackTagCrossRefDao = trackTagCrossRefDao,
            cacheErrorHandler = cacheErrorHandler
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
    fun provideApplyNewRule(
        trackDao: TrackDao,
        trackEntityMapper: TrackEntityMapper,
        @Named("cacheErrorHandler") cacheErrorHandler: ErrorHandler,
        playlistService: RetrofitPlaylistService,
        @Named("networkErrorHandler") networkErrorHandler: ErrorHandler
    ): ApplyNewRule {
        return ApplyNewRule(
            trackDao = trackDao,
            trackEntityMapper = trackEntityMapper,
            cacheErrorHandler = cacheErrorHandler,
            playlistService = playlistService,
            networkErrorHandler = networkErrorHandler
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