package com.hfad.tagalong.domain_common

interface DomainMapper<DomainModel, Model> {

    fun mapToDomainModel(model: Model): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): Model

    fun mapToDomainModelList(modelList: List<Model>): List<DomainModel> {
        return modelList.map(::mapToDomainModel)
    }

    fun mapFromDomainModelList(domainModelList: List<DomainModel>): List<Model> {
        return domainModelList.map(::mapFromDomainModel)
    }

}