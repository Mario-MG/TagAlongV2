package com.mariomg.tagalong.track_network.services

import com.mariomg.tagalong.track_network.model.PlaylistItemsPage
import com.mariomg.tagalong.track_network.model.TrackDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitTrackService {

    @GET("playlists/{id}/tracks")
    suspend fun getItemsInPlaylist(
        @Header("Authorization") auth: String,
        @Path("id") playlistId: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PlaylistItemsPage

    @GET("tracks/{id}")
    suspend fun getTrack(
        @Header("Authorization") auth: String,
        @Path("id") trackId: String,
    ): TrackDto

}