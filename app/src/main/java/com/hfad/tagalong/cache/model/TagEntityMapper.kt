package com.hfad.tagalong.cache.model

import com.hfad.tagalong.domain.util.DomainMapper
import com.hfad.tagalong.tag_domain.Tag
import com.hfad.tagalong.tag_domain.TagInfo

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

    fun mapFromDomainModel(domainInfo: TagInfo): TagEntityPoko {
        return TagEntityPoko(
            tagEntity = TagEntity(
                id = 0,
                name = domainInfo.name
            ),
            size = domainInfo.size
        )
    }

}