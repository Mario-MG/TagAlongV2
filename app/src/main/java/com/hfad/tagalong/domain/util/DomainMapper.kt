package com.hfad.tagalong.domain.util


interface DomainMapper<T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T

    fun toDomainList(modelList: List<T>): List<DomainModel> {
        return modelList.map { mapToDomainModel(it) }
    }

    fun fromDomainList(domainModelList: List<DomainModel>): List<T> {
        return domainModelList.map { mapFromDomainModel(it) }
    }

}