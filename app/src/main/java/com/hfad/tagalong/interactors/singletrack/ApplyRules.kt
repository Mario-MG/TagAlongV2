package com.hfad.tagalong.interactors.singletrack

import com.hfad.tagalong.cache.dao.RuleDao
import com.hfad.tagalong.cache.model.RuleEntityMapper
import com.hfad.tagalong.domain.data.DataState
import com.hfad.tagalong.domain.model.Playlist
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.network.RetrofitPlaylistService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApplyRules(
    private val ruleDao: RuleDao,
    private val ruleEntityMapper: RuleEntityMapper,
    private val playlistService: RetrofitPlaylistService
) {

    fun execute(
        newTag: Tag,
        originalTags: List<Tag>,
        track: Track,
        auth: String
    ): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading)

            val rules = getRulesFulfilledByTags(newTag = newTag, originalTags = originalTags)
            for (rule in rules) {
                addTrackToPlaylist(auth = auth, track = track, playlist = rule.playlist)
            }

            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
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