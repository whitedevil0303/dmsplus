package com.cinematichororuniverse.dmsplus.data.repository

import com.cinematichororuniverse.dmsplus.data.api.BannerApiService
import com.cinematichororuniverse.dmsplus.data.model.Banner
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannerRepository @Inject constructor(
    private val bannerApiService: BannerApiService
) {

    fun getBanners(): Flow<List<Banner>> = flow {
        var retryCount = 0
        val maxRetries = 3
        val initialDelayMs = 1000L
        
        while (retryCount <= maxRetries) {
            try {
                val response = bannerApiService.getBanners()
                if (response.isSuccessful && response.body()?.success == true) {
                    val banners = response.body()?.data?.filter { it.isActive } ?: emptyList()
                    emit(banners.sortedBy { it.sortOrder })
                    return@flow // Success, exit retry loop
                } else {
                    throw Exception("API returned unsuccessful response: ${response.code()}")
                }
            } catch (e: Exception) {
                retryCount++
                if (retryCount > maxRetries) {
                    // All retries exhausted, emit empty list
                    emit(emptyList())
                    throw Exception("Failed to load banners after $maxRetries retries: ${e.message}")
                } else {
                    // Wait before retry with exponential backoff
                    delay(initialDelayMs * (1L shl (retryCount - 1))) // 1s, 2s, 4s
                }
            }
        }
    }
}