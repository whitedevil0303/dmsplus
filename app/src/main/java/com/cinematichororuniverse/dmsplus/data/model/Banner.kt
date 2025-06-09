package com.cinematichororuniverse.dmsplus.data.model

import com.google.gson.annotations.SerializedName

data class BannerResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: List<Banner>,
    @SerializedName("source")
    val source: String
)

data class Banner(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("target_url")
    val targetUrl: String,
    @SerializedName("target_url_type")
    val targetUrlType: String,
    @SerializedName("banner_type")
    val bannerType: String,
    @SerializedName("ads_start_date")
    val adsStartDate: String,
    @SerializedName("ads_end_date")
    val adsEndDate: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("sort_order")
    val sortOrder: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)