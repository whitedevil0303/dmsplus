package com.cinematichororuniverse.dmsplus.di

import com.cinematichororuniverse.dmsplus.data.repository.HorrorContentRepository
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
    fun provideHorrorContentRepository(): HorrorContentRepository {
        return HorrorContentRepository()
    }
}