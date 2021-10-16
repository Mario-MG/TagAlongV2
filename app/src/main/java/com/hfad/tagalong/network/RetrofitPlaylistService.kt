package com.hfad.tagalong.network

import com.hfad.tagalong.network.models.PlaylistDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RetrofitPlaylistService {

    @GET("{id}")
    suspend fun get(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): PlaylistDto

}