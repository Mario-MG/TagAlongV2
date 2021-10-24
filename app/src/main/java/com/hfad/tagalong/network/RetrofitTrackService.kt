package com.hfad.tagalong.network

import com.hfad.tagalong.network.model.PlaylistItemsPage
import com.hfad.tagalong.network.model.TrackDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitTrackService {

    @GET("playlists/{id}/tracks")
    suspend fun getItemsInPlaylist(
        @Header("Authorization") token: String,
        @Path("id") playlistId: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PlaylistItemsPage

    @GET("tracks/{id}")
    suspend fun getTrack(
        @Header("Authorization") token: String,
        @Path("id") trackId: String,
    ): TrackDto

}