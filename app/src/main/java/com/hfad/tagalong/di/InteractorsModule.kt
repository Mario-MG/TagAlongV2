package com.hfad.tagalong.di

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.dao.TagDao
import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.dao.TrackTagCrossRefDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.cache.model.TagEntityMapper
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.interactors.playlists.LoadFirstPlaylistsPage
import com.hfad.tagalong.interactors.playlists.LoadNextPlaylistsPage
import com.hfad.tagalong.interactors.playlisttracks.LoadFirstPlaylistTracksPage
import com.hfad.tagalong.interactors.playlisttracks.LoadNextPlaylistTracksPage
import com.hfad.tagalong.interactors.rules.LoadAllRules
import com.hfad.tagalong.interactors.singletrack.*
import com.hfad.tagalong.interactors.tags.LoadAllTags
import com.hfad.tagalong.interactors.tagtracks.LoadAllTagTracks
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.network.RetrofitTrackService
import com.hfad.tagalong.network.model.PlaylistDtoMapper
import com.hfad.tagalong.network.model.TrackDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

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
    fun provideApplyRules(
        ruleDao: RuleDao,
        ruleEntityMapper: RuleEntityMapper,
        playlistService: RetrofitPlaylistService
    ): ApplyRules {
        return ApplyRules(
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

}