package com.mariomg.tagalong.auth_network.model

import com.mariomg.tagalong.session.model.User
import com.mariomg.tagalong.domain_common.DomainMapper
import com.mariomg.tagalong.network_core.model.UserDto

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