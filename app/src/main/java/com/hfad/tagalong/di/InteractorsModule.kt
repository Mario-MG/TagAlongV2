package com.hfad.tagalong.di

import android.content.SharedPreferences
import com.hfad.tagalong.cache.dao.*
import com.hfad.tagalong.cache.model.PlaylistEntityMapper
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.cache.model.TrackEntityMapper
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
import com.hfad.tagalong.interactors.settings.LoadStayLoggedIn
import com.hfad.tagalong.interactors.settings.SaveStayLoggedIn
import com.hfad.tagalong.interactors.singletrack.*
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.interactors.tagtracks.LoadAllTagTracks
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.RetrofitUserService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.model.TokenDtoMapper
import com.hfad.tagalong.network.model.TrackDtoMapper
import com.hfad.tagalong.network.model.UserDtoMapper
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
        playlistDtoMapper: PlaylistDtoMapper
    ): LoadFirstPlaylistsPage {
        return LoadFirstPlaylistsPage(
            playlistService = playlistService,
            playlistDtoMapper = playlistDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadNextPlaylistsPage(
        playlistService: RetrofitPlaylistService,
        playlistDtoMapper: PlaylistDtoMapper
    ): LoadNextPlaylistsPage {
        return LoadNextPlaylistsPage(
            playlistService = playlistService,
            playlistDtoMapper = playlistDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadFirstPlaylistTracksPage(
        trackService: RetrofitTrackService,
        trackDtoMapper: TrackDtoMapper
    ): LoadFirstPlaylistTracksPage {
        return LoadFirstPlaylistTracksPage(
            trackService = trackService,
            trackDtoMapper = trackDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadNextPlaylistTracksPage(
        trackService: RetrofitTrackService,
        trackDtoMapper: TrackDtoMapper
    ): LoadNextPlaylistTracksPage {
        return LoadNextPlaylistTracksPage(
            trackService = trackService,
            trackDtoMapper = trackDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadAllTags( // TODO: What if an interactor is shared between several viewmodels??
        tagDao: TagDao,
        tagEntityMapper: TagEntityMapper
    ): LoadAllTags {
        return LoadAllTags(
            tagDao = tagDao,
            tagEntityMapper = tagEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadAllTagTracks(
        trackDao: TrackDao,
        trackEntityMapper: TrackEntityMapper
    ): LoadAllTagTracks {
        return LoadAllTagTracks(
            trackDao = trackDao,
            trackEntityMapper = trackEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadTrackTags(
        tagDao: TagDao,
        tagEntityMapper: TagEntityMapper
    ): LoadTrackTags {
        return LoadTrackTags(
            tagDao = tagDao,
            tagEntityMapper = tagEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCreateTag(
        tagDao: TagDao,
        tagEntityMapper: TagEntityMapper
    ): CreateTag {
        return CreateTag(
            tagDao = tagDao,
            tagEntityMapper = tagEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideAddTagToTrack(
        trackDao: TrackDao,
        trackEntityMapper: TrackEntityMapper,
        trackTagCrossRefDao: TrackTagCrossRefDao
    ): AddTagToTrack {
        return AddTagToTrack(
            trackDao = trackDao,
            trackEntityMapper = trackEntityMapper,
            trackTagCrossRefDao = trackTagCrossRefDao,
        )
    }

    @Provides
    @ViewModelScoped
    fun provideApplyExistingRules(
        ruleDao: RuleDao,
        ruleEntityMapper: RuleEntityMapper,
        playlistService: RetrofitPlaylistService
    ): ApplyExistingRules {
        return ApplyExistingRules(
            ruleDao = ruleDao,
            ruleEntityMapper = ruleEntityMapper,
            playlistService = playlistService
        )
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteTagFromTrack(
        trackTagCrossRefDao: TrackTagCrossRefDao,
    ): DeleteTagFromTrack {
        return DeleteTagFromTrack(
            trackTagCrossRefDao = trackTagCrossRefDao,
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadAllRules(
        ruleDao: RuleDao,
        ruleEntityMapper: RuleEntityMapper
    ): LoadAllRules {
        return LoadAllRules(
            ruleDao = ruleDao,
            ruleEntityMapper = ruleEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCreatePlaylist(
        playlistService: RetrofitPlaylistService,
        playlistDtoMapper: PlaylistDtoMapper,
        playlistDao: PlaylistDao,
        playlistEntityMapper: PlaylistEntityMapper
    ): CreatePlaylist {
        return CreatePlaylist(
            playlistService = playlistService,
            playlistDtoMapper = playlistDtoMapper,
            playlistDao = playlistDao,
            playlistEntityMapper = playlistEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCreateRule(
        ruleDao: RuleDao,
        ruleEntityMapper: RuleEntityMapper
    ): CreateRule {
        return CreateRule(
            ruleDao = ruleDao,
            ruleEntityMapper = ruleEntityMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideApplyNewRule(
        trackDao: TrackDao,
        trackEntityMapper: TrackEntityMapper,
        playlistService: RetrofitPlaylistService
    ): ApplyNewRule {
        return ApplyNewRule(
            trackDao = trackDao,
            trackEntityMapper = trackEntityMapper,
            playlistService = playlistService
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetTokenFromCode(
        authService: RetrofitAuthService,
        tokenDtoMapper: TokenDtoMapper
    ): GetTokenFromCode {
        return GetTokenFromCode(
            authService = authService,
            tokenDtoMapper = tokenDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetTokenFromRefreshToken(
        authService: RetrofitAuthService,
        tokenDtoMapper: TokenDtoMapper
    ): GetTokenFromRefreshToken {
        return GetTokenFromRefreshToken(
            authService = authService,
            tokenDtoMapper = tokenDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadUser(
        userService: RetrofitUserService,
        userDtoMapper: UserDtoMapper
    ): LoadUser {
        return LoadUser(
            userService = userService,
            userDtoMapper = userDtoMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadSessionInfo(
        @Named("authSharedPreferences")
        sharedPreferences: SharedPreferences
    ): LoadSessionInfo {
        return LoadSessionInfo(
            sharedPreferences = sharedPreferences
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadStayLoggedIn(
        @Named("defaultSharedPreferences")
        sharedPreferences: SharedPreferences
    ): LoadStayLoggedIn {
        return LoadStayLoggedIn(
            sharedPreferences = sharedPreferences
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSaveStayLoggedIn(
        @Named("defaultSharedPreferences")
        sharedPreferences: SharedPreferences
    ): SaveStayLoggedIn {
        return SaveStayLoggedIn(
            sharedPreferences = sharedPreferences
        )
    }

}