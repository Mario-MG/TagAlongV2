package com.hfad.tagalong.network.model

import com.hfad.tagalong.network.session.model.User
import com.hfad.tagalong.domain.util.DomainMapper

class UserDtoMapper : DomainMapper<UserDto, User> {

    override fun mapToDomainModel(model: UserDto): User {
        return User(
            name = model.displayName,
            id = model.id
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserDto {
        TODO("Not yet implemented")
    }

}