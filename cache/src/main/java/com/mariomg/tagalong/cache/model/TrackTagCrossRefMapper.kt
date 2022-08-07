package com.mariomg.tagalong.cache.model

import com.mariomg.tagalong.tag_domain.Tag
import com.mariomg.tagalong.track_domain.Track

class TrackTagCrossRefMapper(
    private val trackEntityMapper: TrackEntityMapper,
    private val tagEntityMapper: TagEntityMapper
) {

    fun mapFromDomainModel(track: Track, tag: Tag): TrackTagCrossRef =
        TrackTagCrossRef(
            trackId = trackEntityMapper.mapFromDomainModel(track).id,
            tagId = tagEntityMapper.mapFromDomainModel(tag).tagEntity.id
        )

}