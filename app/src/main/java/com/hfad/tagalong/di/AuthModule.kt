package com.hfad.tagalong.di

import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.model.TokenDtoMapper
import com.hfad.tagalong.repository.AuthRepository
import com.hfad.tagalong.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        authService: RetrofitAuthService, // TODO: Should this dependency be an abstraction?
        tokenMapper: TokenDtoMapper
    ): AuthRepository = AuthRepositoryImpl(
        authService = authService,
        tokenMapper = tokenMapper
    )

}