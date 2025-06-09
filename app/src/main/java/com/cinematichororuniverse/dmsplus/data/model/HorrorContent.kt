package com.cinematichororuniverse.dmsplus.data.model

data class HorrorContent(
    val id: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val hlsUrl: String,
    val duration: Long, // in milliseconds
    val creatorName: String,
    val creatorAvatar: String,
    val category: ContentCategory,
    val tags: List<String>,
    val rating: Float,
    val viewCount: Long,
    val releaseDate: Long, // timestamp
    val isExclusive: Boolean = false,
    val isPremium: Boolean = false
)

enum class ContentCategory {
    HORROR,
    MYSTERY,
    DOCUMENTARY,
    BEHIND_SCENES,
    LIVE_STREAM,
    EXCLUSIVE
}

data class Creator(
    val id: String,
    val name: String,
    val displayName: String,
    val avatarUrl: String,
    val description: String,
    val subscriberCount: Long,
    val isVerified: Boolean,
    val socialLinks: Map<String, String> = emptyMap()
)

data class HorrorSeries(
    val id: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val creator: Creator,
    val episodes: List<HorrorContent>,
    val totalEpisodes: Int,
    val category: ContentCategory,
    val isCompleted: Boolean
)