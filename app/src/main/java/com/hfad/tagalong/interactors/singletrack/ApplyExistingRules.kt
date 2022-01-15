package com.hfad.tagalong.interactors.singletrack

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.track_domain.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApplyExistingRules(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper,
    private val cacheErrorHandler: ErrorHandler,
    private val playlistService: RetrofitPlaylistService,
    private val networkErrorHandler: ErrorHandler
) {

    fun execute(
        newTag: Tag,
        originalTags: List<Tag>,
        track: Track,
        auth: String
    ): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading(true))

            val rules = getRulesFulfilledByTags(newTag = newTag, originalTags = originalTags)

            try {
                for (rule in rules) {
                    addTrackToPlaylist(auth = auth, track = track, playlist = rule.playlist)
                }

                emit(DataState.Success(Unit))
            } catch (e: Exception) {
                emit(DataState.Error(networkErrorHandler.parseError(e)))
            }

        } catch (e: Exception) {
            emit(DataState.Error(cacheErrorHandler.parseError(e)))
        } finally {
            emit(DataState.Loading(false))
        }
    }

    private suspend fun getRulesFulfilledByTags(newTag: Tag, originalTags: List<Tag>): List<Rule> {
        return ruleEntityMapper.toDomainList(
            ruleDao.getRulesFulfilledByTagIds(newTag.id, *originalTags.map(Tag::id).toLongArray())
        )
    }

    private suspend fun addTrackToPlaylist(auth: String, track: Track, playlist: Playlist) {
        playlistService.addTracksToPlaylist(
            auth = auth,
            playlistId = playlist.id,
            trackUris = track.uri
        )
    }

}