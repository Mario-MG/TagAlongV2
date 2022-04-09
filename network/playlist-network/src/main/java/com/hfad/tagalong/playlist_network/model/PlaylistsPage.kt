package com.hfad.tagalong.playlist_network.model

import com.google.gson.annotations.SerializedName

data class PlaylistsPage (
    @SerializedName("href") var href : String,
    @SerializedName("items") var items : List<PlaylistDto>,
    @SerializedName("limit") var limit : Int,
    @SerializedName("next") var next : String,
    @SerializedName("offset") var offset : Int,
    @SerializedName("previous") var previous : String,
    @SerializedName("total") var total : Int
)