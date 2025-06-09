package com.cinematichororuniverse.dmsplus.data.api

import com.cinematichororuniverse.dmsplus.data.model.BannerResponse
import retrofit2.Response
import retrofit2.http.GET

interface BannerApiService {
    @GET("api/v1/banners")
    suspend fun getBanners(): Response<BannerResponse>
}