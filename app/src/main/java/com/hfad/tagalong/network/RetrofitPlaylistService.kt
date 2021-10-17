package com.hfad.tagalong.network

import com.hfad.tagalong.network.model.PlaylistDto
import com.hfad.tagalong.network.model.PlaylistListDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitPlaylistService {

    @GET("playlists/{id}")
    suspend fun getById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): PlaylistDto

    @GET("me/playlists")
    suspend fun getList(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PlaylistListDto

}