package com.example.myapp.di

import com.example.myapp.data.CipherRepositoryImpl
import com.example.myapp.domain.CipherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCipherRepository(): CipherRepository {
        return CipherRepositoryImpl()
    }
}