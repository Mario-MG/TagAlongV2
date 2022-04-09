package com.hfad.tagalong.auth_network.model

import com.hfad.tagalong.session.model.Token
import com.hfad.tagalong.domain_common.DomainMapper

class TokenDtoMapper : DomainMapper<Token, TokenDto> {

    override fun mapToDomainModel(model: TokenDto): Token {
        return Token(
            accessToken = model.accessToken,
            refreshToken = model.refreshToken
        )
    }

    override fun mapFromDomainModel(domainModel: Token): TokenDto {
        TODO("Not yet implemented")
    }

}