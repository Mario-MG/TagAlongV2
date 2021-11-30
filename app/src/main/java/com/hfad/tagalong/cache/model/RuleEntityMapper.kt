package com.hfad.tagalong.cache.model

import com.hfad.tagalong.domain.model.Rule
import com.hfad.tagalong.domain.util.DomainMapper

class RuleEntityMapper(
    private val tagEntityMapper: TagEntityMapper
) : DomainMapper<RuleWithTags, Rule> {

    override fun mapToDomainModel(model: RuleWithTags): Rule {
        return Rule(
            id = model.rule.id,
            playlistId = model.rule.playlistId,
            optionality = model.rule.optionality,
            autoUpdate = model.rule.autoUpdate,
            tags = tagEntityMapper.toDomainList(model.tags.map { tag -> TagEntityPoko(tag, 0) }) // TODO: Improve this
        )
    }

    override fun mapFromDomainModel(domainModel: Rule): RuleWithTags {
        return RuleWithTags(
            RuleEntity(
                id = domainModel.id,
                playlistId = domainModel.playlistId,
                optionality = domainModel.optionality,
                autoUpdate = domainModel.autoUpdate
            ),
            tagEntityMapper.fromDomainList(domainModel.tags).map { it.tagEntity } // TODO: Improve this
        )
    }

}