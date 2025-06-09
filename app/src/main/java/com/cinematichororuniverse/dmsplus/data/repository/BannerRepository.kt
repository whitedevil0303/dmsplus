package com.cinematichororuniverse.dmsplus.data.repository

import com.cinematichororuniverse.dmsplus.data.api.BannerApiService
import com.cinematichororuniverse.dmsplus.data.model.Banner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannerRepository @Inject constructor(
    private val bannerApiService: BannerApiService
) {
    fun getBanners(): Flow<List<Banner>> = flow {
        try {
            val response = bannerApiService.getBanners()
            if (response.isSuccessful && response.body()?.success == true) {
                val banners = response.body()?.data?.filter { it.isActive } ?: emptyList()
                emit(banners.sortedBy { it.sortOrder })
            } else {
                // Emit empty list if API fails, could also emit error state
                emit(emptyList())
            }
        } catch (e: Exception) {
            // Handle network errors
            emit(emptyList())
        }
    }
}