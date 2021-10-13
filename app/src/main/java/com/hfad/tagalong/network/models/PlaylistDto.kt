package com.hfad.tagalong.network.models

import com.google.gson.annotations.SerializedName

internal data class PlaylistDto(
    @SerializedName("description") var description : String,
    @SerializedName("href") var href : String,
    @SerializedName("id") var id : String,
    @SerializedName("images") var images : List<Image>,
    @SerializedName("name") var name : String,
    @SerializedName("owner") var owner : User,
    @SerializedName("primary_color") var primaryColor : String?,
    @SerializedName("public") var public : Boolean,
    @SerializedName("tracks") var tracks : PlaylistTracksRef,
    @SerializedName("type") var type : String,
    @SerializedName("uri") var uri : String
)
