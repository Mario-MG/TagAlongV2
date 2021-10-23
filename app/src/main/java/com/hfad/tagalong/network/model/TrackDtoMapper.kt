package com.hfad.tagalong.network.model

import com.hfad.tagalong.domain.model.Track
import com.hfad.tagalong.domain.util.DomainMapper

class TrackDtoMapper: DomainMapper<TrackDto, Track> {

    override fun mapToDomainModel(model: TrackDto): Track {
        return Track(
            id = model.id,
            name = model.name,
            album = model.album.name,
            artists = model.artists.map{ it.name },
            imageUrl = if (model.album.images.isNotEmpty()) model.album.images[0].url else null
        )
    }

    override fun mapFromDomainModel(domainModel: Track): TrackDto {
        TODO("Not yet implemented")
    }

    fun toDomainList(modelList: List<TrackDto>): List<Track>{
        return modelList.map { mapToDomainModel(it) }
    }

}