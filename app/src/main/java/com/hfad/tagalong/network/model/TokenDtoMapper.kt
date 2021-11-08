package com.hfad.tagalong.network.model

import com.hfad.tagalong.domain.model.Token
import com.hfad.tagalong.domain.util.DomainMapper

class TokenDtoMapper : DomainMapper<TokenDto, Token> {

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