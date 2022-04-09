package com.hfad.tagalong.di

import com.hfad.tagalong.auth_interactors.*
import com.hfad.tagalong.auth_interactors_core.repositories.AuthCacheRepository
import com.hfad.tagalong.auth_interactors_core.repositories.AuthNetworkRepository
import com.hfad.tagalong.auth_interactors_core.session.SessionManager
import com.hfad.tagalong.interactors_core.data.ErrorMapper
import com.hfad.tagalong.playlist_interactors.AddTracksToPlaylists
import com.hfad.tagalong.playlist_interactors.CreatePlaylist
import com.hfad.tagalong.playlist_interactors.LoadPlaylistsPage
import com.hfad.tagalong.playlist_interactors_core.repositories.PlaylistCacheRepository
import com.hfad.tagalong.playlist_interactors_core.repositories.PlaylistNetworkRepository
import com.hfad.tagalong.rule_interactors.CreateRule
import com.hfad.tagalong.rule_interactors.LoadAllRules
import com.hfad.tagalong.rule_interactors.LoadRulesForTags
import com.hfad.tagalong.rule_interactors_core.repositories.RuleCacheRepository
import com.hfad.tagalong.settings_interactors.LoadStayLoggedIn
import com.hfad.tagalong.settings_interactors.SaveStayLoggedIn
import com.hfad.tagalong.settings_interactors_core.repositories.SettingsRepository
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
    fun provideLoadRulesForTags(
        ruleCacheRepository: RuleCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): LoadRulesForTags {
        return LoadRulesForTags(
            ruleCacheRepository = ruleCacheRepository,
            cacheErrorMapper = cacheErrorMapper
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
        ruleCacheRepository: RuleCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): LoadAllRules {
        return LoadAllRules(
            ruleCacheRepository = ruleCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCreatePlaylist(
        playlistNetworkRepository: PlaylistNetworkRepository,
        @Named("networkErrorMapper") networkErrorMapper: ErrorMapper,
        playlistCacheRepository: PlaylistCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): CreatePlaylist {
        return CreatePlaylist(
            playlistNetworkRepository = playlistNetworkRepository,
            networkErrorMapper = networkErrorMapper,
            playlistCacheRepository = playlistCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideCreateRule(
        ruleCacheRepository: RuleCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): CreateRule {
        return CreateRule(
            ruleCacheRepository = ruleCacheRepository,
            cacheErrorMapper = cacheErrorMapper
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
    ): AddTracksToPlaylists {
        return AddTracksToPlaylists(
            playlistNetworkRepository = playlistNetworkRepository,
            networkErrorMapper = networkErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLogIn(
        authNetworkRepository: AuthNetworkRepository,
        @Named("networkErrorMapper") networkErrorMapper: ErrorMapper
    ): GetNewSessionData {
        return GetNewSessionData(
            authNetworkRepository = authNetworkRepository,
            networkErrorMapper = networkErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLogOut(
        sessionManager: SessionManager
    ): LogOut {
        return LogOut(
            sessionManager = sessionManager
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadSessionData(
        authCacheRepository: AuthCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): LoadSessionData {
        return LoadSessionData(
            authCacheRepository = authCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSaveSessionData(
        authCacheRepository: AuthCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): SaveSessionData {
        return SaveSessionData(
            authCacheRepository = authCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteSessionData(
        authCacheRepository: AuthCacheRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): DeleteSessionData {
        return DeleteSessionData(
            authCacheRepository = authCacheRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideRefreshSessionData(
        authNetworkRepository: AuthNetworkRepository,
        @Named("networkErrorMapper") networkErrorMapper: ErrorMapper
    ): RefreshSessionData {
        return RefreshSessionData(
            authNetworkRepository = authNetworkRepository,
            networkErrorMapper = networkErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLoadStayLoggedIn(
        settingsRepository: SettingsRepository,
        @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): LoadStayLoggedIn {
        return LoadStayLoggedIn(
            settingsRepository = settingsRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

    @Provides
    @ViewModelScoped
    fun provideSaveStayLoggedIn(
        settingsRepository: SettingsRepository,
    @Named("cacheErrorMapper") cacheErrorMapper: ErrorMapper
    ): SaveStayLoggedIn {
        return SaveStayLoggedIn(
            settingsRepository = settingsRepository,
            cacheErrorMapper = cacheErrorMapper
        )
    }

}