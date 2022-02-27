package com.hfad.tagalong.auth_network.model

import com.hfad.tagalong.session.model.User
import com.hfad.tagalong.domain_common.DomainMapper
import com.hfad.tagalong.network_core.model.UserDto

class UserDtoMapper : DomainMapper<User, UserDto> {

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