package com.hfad.tagalong.presentation.adapters

import android.os.Parcel
import android.os.Parcelable
import com.hfad.tagalong.track_domain.Track

data class TrackParcelable(
    val track: Track
) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.apply {
            writeString(track.id)
            writeString(track.name)
            writeString(track.album)
            writeStringList(track.artists)
            writeString(track.imageUrl)
            writeString(track.uri)
        }
    }

    companion object CREATOR : Parcelable.Creator<Track> {
        override fun createFromParcel(parcel: Parcel): Track {
            return Track(
                id = parcel.readString()!!,
                name = parcel.readString()!!,
                album = parcel.readString()!!,
                artists = parcel.createStringArrayList()!!,
                imageUrl = parcel.readString(),
                uri = parcel.readString()!!
            )
        }

        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }
    }

}