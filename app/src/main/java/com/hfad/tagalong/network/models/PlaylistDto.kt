package com.hfad.tagalong.network.models

import com.google.gson.annotations.SerializedName

data class PlaylistDto(
    @SerializedName("description") var description : String,
    @SerializedName("href") var href : String,
    @SerializedName("id") var id : String,
    @SerializedName("images") var images : List<ImageDto>,
    @SerializedName("name") var name : String,
    @SerializedName("owner") var owner : UserDto,
    @SerializedName("primary_color") var primaryColor : String?,
    @SerializedName("public") var public : Boolean,
    @SerializedName("tracks") var tracks : PlaylistTracksRefDto,
    @SerializedName("type") var type : String,
    @SerializedName("uri") var uri : String
)
