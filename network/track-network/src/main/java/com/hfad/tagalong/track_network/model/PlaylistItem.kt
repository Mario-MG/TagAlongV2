package com.hfad.tagalong.track_network.model

import com.google.gson.annotations.SerializedName
import com.hfad.tagalong.network_core.model.UserDto

data class PlaylistItem (
    @SerializedName("added_at") var added_at : String?,
    @SerializedName("added_by") var added_by : UserDto?,
    @SerializedName("is_local") var is_local : Boolean,
    @SerializedName("track") var track : TrackDto
)
