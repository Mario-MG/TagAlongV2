package com.mariomg.tagalong.playlist_network.model

import com.google.gson.annotations.SerializedName

data class PlaylistTracksRefDto (
    @SerializedName("href") var href : String,
    @SerializedName("total") var total : Int
)
