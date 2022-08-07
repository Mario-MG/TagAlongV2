package com.mariomg.tagalong.playlist_network.services

import com.google.gson.JsonObject
import com.mariomg.tagalong.playlist_network.model.PlaylistDto
import com.mariomg.tagalong.playlist_network.model.PlaylistsPage
import retrofit2.http.*

interface RetrofitPlaylistService {

    @GET("playlists/{id}")
    suspend fun getById(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): PlaylistDto

    @GET("me/playlists")
    suspend fun getList(
        @Header("Authorization") auth: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PlaylistsPage

    @POST("users/{user_id}/playlists")
    suspend fun create(
        @Header("Authorization") auth: String,
        @Path("user_id") userId: String,
        @Body body: JsonObject
    ): PlaylistDto

    @POST("playlists/{id}/tracks")
    suspend fun addTracksToPlaylist(
        @Header("Authorization") auth: String,
        @Path("id") playlistId: String,
        @Query("uris") trackUris: String
    )

}