package com.cinematichororuniverse.dmsplus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinematichororuniverse.dmsplus.data.model.Banner
import com.cinematichororuniverse.dmsplus.data.model.Creator
import com.cinematichororuniverse.dmsplus.data.model.HorrorContent
import com.cinematichororuniverse.dmsplus.data.repository.BannerRepository
import com.cinematichororuniverse.dmsplus.data.repository.HorrorContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val banners: List<Banner> = emptyList(),
    val bannerError: String? = null,
    val bannerLoading: Boolean = false,
    val featuredContent: List<HorrorContent> = emptyList(),
    val trendingContent: List<HorrorContent> = emptyList(),
    val exclusiveContent: List<HorrorContent> = emptyList(),
    val recentContent: List<HorrorContent> = emptyList(),
    val creators: List<Creator> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HorrorContentRepository,
    private val bannerRepository: BannerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeContent()
        loadBanners()
    }

    private fun loadHomeContent() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                // Load featured content
                repository.getFeaturedContent().collect { featured ->
                    _uiState.value = _uiState.value.copy(featuredContent = featured)
                }
                
                // Load trending content
                repository.getTrendingContent().collect { trending ->
                    _uiState.value = _uiState.value.copy(trendingContent = trending)
                }
                
                // Load exclusive content
                repository.getExclusiveContent().collect { exclusive ->
                    _uiState.value = _uiState.value.copy(exclusiveContent = exclusive)
                }
                
                // Load recent content
                repository.getAllContent().collect { all ->
                    val recent = all.sortedByDescending { content -> content.releaseDate }.take(10)
                    _uiState.value = _uiState.value.copy(recentContent = recent)
                }
                
                // Load creators
                repository.getCreators().collect { creators ->
                    _uiState.value = _uiState.value.copy(
                        creators = creators,
                        isLoading = false
                    )
                }
                
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Failed to load content"
                )
            }
        }
    }
    
    private fun loadBanners() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(bannerLoading = true, bannerError = null)
            
            try {
                bannerRepository.getBanners().collect { banners ->
                    _uiState.value = _uiState.value.copy(
                        banners = banners,
                        bannerLoading = false,
                        bannerError = null
                    )
                }
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    bannerLoading = false,
                    bannerError = exception.message ?: "Failed to load banners"
                )
            }
        }
    }

    fun retryLoading() {
        loadHomeContent()
    }

    fun refreshContent() {
        loadHomeContent()
        loadBanners()
    }

    fun retryBanners() {
        loadBanners()
    }
    
    fun onBannerClick(banner: Banner) {
        // Handle banner click - could navigate to video or external URL
        println("Banner clicked: ${banner.title} - ${banner.targetUrl}")
        // TODO: Implement navigation based on banner.targetUrlType and banner.targetUrl
    }
}