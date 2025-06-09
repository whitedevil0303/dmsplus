package com.cinematichororuniverse.dmsplus.data.repository

import com.cinematichororuniverse.dmsplus.data.model.ContentCategory
import com.cinematichororuniverse.dmsplus.data.model.Creator
import com.cinematichororuniverse.dmsplus.data.model.HorrorContent
import com.cinematichororuniverse.dmsplus.data.model.HorrorSeries
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HorrorContentRepository @Inject constructor() {

    // Mock data for demonstration - replace with actual API calls
    private val mockCreators = listOf(
        Creator(
            id = "1",
            name = "Diary Misteri Sara",
            displayName = "Diary Misteri Sara",
            avatarUrl = "https://example.com/sara_avatar.jpg",
            description = "Indonesia's premier horror content creator",
            subscriberCount = 1500000,
            isVerified = true,
            socialLinks = mapOf(
                "youtube" to "https://youtube.com/@diarymisterisara",
                "instagram" to "https://instagram.com/diarymisterisara"
            )
        ),
        Creator(
            id = "2",
            name = "Horror Indonesia",
            displayName = "Horror Indonesia Official",
            avatarUrl = "https://example.com/horror_indo_avatar.jpg",
            description = "Official horror content from Indonesia",
            subscriberCount = 800000,
            isVerified = true
        )
    )

    private val mockHorrorContent = listOf(
        HorrorContent(
            id = "1",
            title = "Misteri Rumah Tua di Jalan Raya",
            description = "Cerita mengerikan tentang rumah berhantu yang telah ditinggalkan selama 50 tahun",
            thumbnailUrl = "https://example.com/thumbnail1.jpg",
            hlsUrl = "https://example.com/video1.m3u8",
            duration = 1800000, // 30 minutes
            creatorName = "Diary Misteri Sara",
            creatorAvatar = "https://example.com/sara_avatar.jpg",
            category = ContentCategory.HORROR,
            tags = listOf("hantu", "rumah tua", "misteri"),
            rating = 4.8f,
            viewCount = 250000,
            releaseDate = System.currentTimeMillis() - 86400000, // Yesterday
            isExclusive = true
        ),
        HorrorContent(
            id = "2",
            title = "Legenda Pocong Jembatan Merah",
            description = "Investigasi mendalam tentang penampakan pocong di jembatan bersejarah",
            thumbnailUrl = "https://example.com/thumbnail2.jpg",
            hlsUrl = "https://example.com/video2.m3u8",
            duration = 2400000, // 40 minutes
            creatorName = "Diary Misteri Sara",
            creatorAvatar = "https://example.com/sara_avatar.jpg",
            category = ContentCategory.MYSTERY,
            tags = listOf("pocong", "jembatan", "legenda"),
            rating = 4.6f,
            viewCount = 180000,
            releaseDate = System.currentTimeMillis() - 172800000, // 2 days ago
            isPremium = true
        ),
        HorrorContent(
            id = "3",
            title = "Dokumenter: Sejarah Kelam Benteng Rotterdam",
            description = "Mengungkap kisah-kisah mengerikan di balik benteng bersejarah",
            thumbnailUrl = "https://example.com/thumbnail3.jpg",
            hlsUrl = "https://example.com/video3.m3u8",
            duration = 3600000, // 1 hour
            creatorName = "Horror Indonesia Official",
            creatorAvatar = "https://example.com/horror_indo_avatar.jpg",
            category = ContentCategory.DOCUMENTARY,
            tags = listOf("sejarah", "benteng", "dokumenter"),
            rating = 4.9f,
            viewCount = 420000,
            releaseDate = System.currentTimeMillis() - 259200000, // 3 days ago
            isExclusive = false
        ),
        HorrorContent(
            id = "4",
            title = "Behind The Scenes: Pembuatan Film Horor",
            description = "Melihat proses pembuatan film horor Indonesia terbaru",
            thumbnailUrl = "https://example.com/thumbnail4.jpg",
            hlsUrl = "https://example.com/video4.m3u8",
            duration = 1200000, // 20 minutes
            creatorName = "Horror Indonesia Official",
            creatorAvatar = "https://example.com/horror_indo_avatar.jpg",
            category = ContentCategory.BEHIND_SCENES,
            tags = listOf("behind the scenes", "film", "produksi"),
            rating = 4.4f,
            viewCount = 95000,
            releaseDate = System.currentTimeMillis() - 345600000, // 4 days ago
            isPremium = false
        ),
        HorrorContent(
            id = "5",
            title = "Live Investigation: Rumah Sakit Tua",
            description = "Investigasi langsung di rumah sakit tua yang ditinggalkan",
            thumbnailUrl = "https://example.com/thumbnail5.jpg",
            hlsUrl = "https://example.com/video5.m3u8",
            duration = 5400000, // 90 minutes
            creatorName = "Diary Misteri Sara",
            creatorAvatar = "https://example.com/sara_avatar.jpg",
            category = ContentCategory.LIVE_STREAM,
            tags = listOf("live", "rumah sakit", "investigasi"),
            rating = 4.7f,
            viewCount = 320000,
            releaseDate = System.currentTimeMillis() - 432000000, // 5 days ago
            isExclusive = true
        )
    )

    fun getFeaturedContent(): Flow<List<HorrorContent>> = flow {
        delay(1000) // Simulate network delay
        emit(mockHorrorContent.take(3))
    }

    fun getContentByCategory(category: ContentCategory): Flow<List<HorrorContent>> = flow {
        delay(800)
        emit(mockHorrorContent.filter { it.category == category })
    }

    fun getAllContent(): Flow<List<HorrorContent>> = flow {
        delay(500)
        emit(mockHorrorContent)
    }

    suspend fun getContentById(id: String): HorrorContent? {
        delay(300)
        return mockHorrorContent.find { it.id == id }
    }

    fun getCreatorContent(creatorName: String): Flow<List<HorrorContent>> = flow {
        delay(600)
        emit(mockHorrorContent.filter { it.creatorName == creatorName })
    }

    fun searchContent(query: String): Flow<List<HorrorContent>> = flow {
        delay(400)
        val searchResults = mockHorrorContent.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true) ||
            it.tags.any { tag -> tag.contains(query, ignoreCase = true) }
        }
        emit(searchResults)
    }

    fun getTrendingContent(): Flow<List<HorrorContent>> = flow {
        delay(700)
        emit(mockHorrorContent.sortedByDescending { it.viewCount }.take(5))
    }

    fun getExclusiveContent(): Flow<List<HorrorContent>> = flow {
        delay(900)
        emit(mockHorrorContent.filter { it.isExclusive })
    }

    fun getPremiumContent(): Flow<List<HorrorContent>> = flow {
        delay(800)
        emit(mockHorrorContent.filter { it.isPremium })
    }

    fun getCreators(): Flow<List<Creator>> = flow {
        delay(500)
        emit(mockCreators)
    }

    suspend fun getCreatorById(creatorId: String): Creator? {
        delay(300)
        return mockCreators.find { it.id == creatorId }
    }

    // Mock series data
    fun getHorrorSeries(): Flow<List<HorrorSeries>> = flow {
        delay(600)
        val series = listOf(
            HorrorSeries(
                id = "series_1",
                title = "Misteri Indonesia",
                description = "Serial dokumenter tentang misteri-misteri di Indonesia",
                thumbnailUrl = "https://example.com/series1.jpg",
                creator = mockCreators[0],
                episodes = mockHorrorContent.take(3),
                totalEpisodes = 10,
                category = ContentCategory.MYSTERY,
                isCompleted = false
            ),
            HorrorSeries(
                id = "series_2",
                title = "Horror Klasik Indonesia",
                description = "Koleksi cerita horor klasik dari berbagai daerah",
                thumbnailUrl = "https://example.com/series2.jpg",
                creator = mockCreators[1],
                episodes = mockHorrorContent.drop(2).take(2),
                totalEpisodes = 8,
                category = ContentCategory.HORROR,
                isCompleted = true
            )
        )
        emit(series)
    }
}