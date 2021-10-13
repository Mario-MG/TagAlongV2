package com.hfad.tagalong.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RetrofitPlaylistService {

    @GET("{id}")
    suspend fun get(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    )

}