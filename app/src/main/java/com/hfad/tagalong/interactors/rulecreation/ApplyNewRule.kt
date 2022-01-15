package com.hfad.tagalong.interactors.rulecreation

import com.hfad.tagalong.cache.dao.TrackDao
import com.hfad.tagalong.cache.model.TrackEntityMapper
import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.interactors.data.DataState
import com.hfad.tagalong.interactors.data.ErrorHandler
import com.hfad.tagalong.network.RetrofitPlaylistService
import com.hfad.tagalong.playlist_domain.Playlist
import com.hfad.tagalong.tag_domain.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApplyNewRule(
    private val trackDao: TrackDao,
    private val trackEntityMapper: TrackEntityMapper,
    private val cacheErrorHandler: ErrorHandler,
    private val playlistService: RetrofitPlaylistService,
    private val networkErrorHandler: ErrorHandler
) {

    fun execute(
        rule: Rule,
        auth: String
    ): Flow<DataState<Unit>> = flow {
        try {
            emit(DataState.Loading(true))

            val tracksForRule = getTracksForRule(rule)

            try {
                addTracksToPlaylist(
                    tracks = tracksForRule,
                    playlist = rule.playlist,
                    auth = auth
                )

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

    private suspend fun getTracksForRule(rule: Rule): List<Track> {
        val tagIds = rule.tags.map(Tag::id).toLongArray()
        return trackEntityMapper.toDomainList(
            if (rule.optionality)
                trackDao.getTracksWithAnyOfTheTagsById(*tagIds)
            else
                trackDao.getTracksWithAllOfTheTagsById(*tagIds)
        )
    }

    private suspend fun addTracksToPlaylist(tracks: List<Track>, playlist: Playlist, auth: String) {
        playlistService.addTracksToPlaylist(
            auth = auth,
            playlistId = playlist.id,
            trackUris = tracks.map(Track::uri).joinToString(",") // TODO: Handle limit of 100 tracks
        )
    }

}