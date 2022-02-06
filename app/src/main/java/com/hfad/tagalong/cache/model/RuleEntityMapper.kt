package com.hfad.tagalong.cache.model

import com.hfad.tagalong.domain.util.DomainMapper
import com.hfad.tagalong.rule_domain.Rule
import com.hfad.tagalong.rule_domain.RuleInfo

class RuleEntityMapper(
    private val playlistEntityMapper: PlaylistEntityMapper,
    private val tagEntityMapper: TagEntityMapper
) : DomainMapper<RuleEntityPoko, Rule> {

    override fun mapToDomainModel(model: RuleEntityPoko): Rule {
        return Rule(
            id = model.rule.id,
            playlist = playlistEntityMapper.mapToDomainModel(model.playlist),
            optionality = model.rule.optionality,
            autoUpdate = model.rule.autoUpdate,
            tags = tagEntityMapper.toDomainList(model.tags.map { tag -> TagEntityPoko(tag, 0) }) // TODO: Improve this
        )
    }

    override fun mapFromDomainModel(domainModel: Rule): RuleEntityPoko {
        return RuleEntityPoko(
            rule = RuleEntity(
                id = domainModel.id,
                optionality = domainModel.optionality,
                autoUpdate = domainModel.autoUpdate
            ),
            playlist = playlistEntityMapper.mapFromDomainModel(domainModel.playlist),
            tags = tagEntityMapper.fromDomainList(domainModel.tags).map { it.tagEntity } // TODO: Improve this
        )
    }

    fun mapFromDomainModel(domainInfo: RuleInfo): RuleEntityPoko {
        return RuleEntityPoko(
            rule = RuleEntity(
                id = 0,
                optionality = domainInfo.optionality,
                autoUpdate = domainInfo.autoUpdate
            ),
            playlist = playlistEntityMapper.mapFromDomainModel(domainInfo.playlist),
            tags = tagEntityMapper.fromDomainList(domainInfo.tags).map { it.tagEntity } // TODO: Improve this
        )
    }

}