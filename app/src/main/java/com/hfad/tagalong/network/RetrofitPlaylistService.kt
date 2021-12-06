package com.hfad.tagalong.network

import com.google.gson.JsonObject
import com.hfad.tagalong.network.model.PlaylistDto
import com.hfad.tagalong.network.model.PlaylistsPage
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

}