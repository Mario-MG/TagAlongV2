package com.hfad.tagalong.cache.model

import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.util.DomainMapper

class TagEntityMapper : DomainMapper<TagEntityPoko, Tag> {

    override fun mapToDomainModel(model: TagEntityPoko): Tag {
        return Tag(
            id = model.tagEntity.id,
            name = model.tagEntity.name,
            size = model.size
        )
    }

    override fun mapFromDomainModel(domainModel: Tag): TagEntityPoko {
        return TagEntityPoko(
            tagEntity = TagEntity(
                id = domainModel.id,
                name = domainModel.name
            ),
            size = domainModel.size
        )
    }

}