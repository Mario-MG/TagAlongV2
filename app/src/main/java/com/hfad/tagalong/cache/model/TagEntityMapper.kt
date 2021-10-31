package com.hfad.tagalong.cache.model

import com.hfad.tagalong.domain.model.Tag
import com.hfad.tagalong.domain.util.DomainMapper

class TagEntityMapper : DomainMapper<TagEntity, Tag> {

    override fun mapToDomainModel(model: TagEntity): Tag {
        return Tag(id = model.id, name = model.name)
    }

    override fun mapFromDomainModel(domainModel: Tag): TagEntity {
        return TagEntity(id = domainModel.id, name = domainModel.name)
    }

}