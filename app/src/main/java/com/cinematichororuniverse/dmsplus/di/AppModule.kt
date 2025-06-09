package com.cinematichororuniverse.dmsplus.di

import com.cinematichororuniverse.dmsplus.data.api.BannerApiService
import com.cinematichororuniverse.dmsplus.data.repository.HorrorContentRepository
import com.cinematichororuniverse.dmsplus.data.repository.BannerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://tulalit.dmsplus.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideBannerApiService(retrofit: Retrofit): BannerApiService {
        return retrofit.create(BannerApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideBannerRepository(bannerApiService: BannerApiService): BannerRepository {
        return BannerRepository(bannerApiService)
    }
    
    @Provides
    @Singleton
    fun provideHorrorContentRepository(): HorrorContentRepository {
        return HorrorContentRepository()
    }
}