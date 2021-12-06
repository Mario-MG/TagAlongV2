package com.hfad.tagalong.di

import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.hfad.tagalong.network.RetrofitAuthService
import com.hfad.tagalong.network.model.TokenDtoMapper
import com.hfad.tagalong.presentation.BaseApplication
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
        tokenMapper: TokenDtoMapper,
        sharedPreferences: SharedPreferences
    ): AuthRepository = AuthRepositoryImpl(
        authService = authService,
        tokenMapper = tokenMapper,
        sharedPreferences = sharedPreferences
    )

    @Singleton
    @Provides
    fun provideAuthSharedPreferences(
        application: BaseApplication
    ): SharedPreferences {
        val masterKey = MasterKey.Builder(application, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            application,
            AUTH_SHARED_PREFS,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}