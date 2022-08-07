package com.mariomg.tagalong.track_network.model

import com.google.gson.annotations.SerializedName

data class ArtistDto (
    @SerializedName("href") var href : String,
    @SerializedName("id") var id : String,
    @SerializedName("name") var name : String,
    @SerializedName("type") var type : String,
    @SerializedName("uri") var uri : String
)