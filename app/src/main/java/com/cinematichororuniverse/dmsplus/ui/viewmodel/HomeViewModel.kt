package com.cinematichororuniverse.dmsplus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinematichororuniverse.dmsplus.data.model.Banner
import com.cinematichororuniverse.dmsplus.data.model.ContentCategory
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
    }

    fun loadHomeContent() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                // Load all content in parallel
                val bannersFlow = bannerRepository.getBanners()
                val featuredFlow = repository.getFeaturedContent()
                val trendingFlow = repository.getTrendingContent()
                val exclusiveFlow = repository.getExclusiveContent()
                val recentFlow = repository.getAllContent()
                val creatorsFlow = repository.getCreators()

                combine(
                    bannersFlow,
                    featuredFlow,
                    trendingFlow,
                    exclusiveFlow,
                    recentFlow,
                    creatorsFlow
                ) { flows ->
                    val banners = flows[0] as List<Banner>
                    val featured = flows[1] as List<HorrorContent>
                    val trending = flows[2] as List<HorrorContent>
                    val exclusive = flows[3] as List<HorrorContent>
                    val recent = flows[4] as List<HorrorContent>
                    val creators = flows[5] as List<Creator>
                    
                    HomeUiState(
                        isLoading = false,
                        banners = banners,
                        featuredContent = featured,
                        trendingContent = trending,
                        exclusiveContent = exclusive,
                        recentContent = recent.sortedByDescending { it.releaseDate }.take(10),
                        creators = creators
                    )
                }.catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Unknown error occurred"
                    )
                }.collect { newState ->
                    _uiState.value = newState
                }
                
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Failed to load content"
                )
            }
        }
    }

    fun refreshContent() {
        loadHomeContent()
    }

    fun retryLoading() {
        loadHomeContent()
    }
    
    fun onBannerClick(banner: Banner) {
        // Handle banner click - could navigate to video or external URL
        // For now, we'll just log it
        println("Banner clicked: ${banner.title} - ${banner.targetUrl}")
        // TODO: Implement navigation based on banner.targetUrlType and banner.targetUrl
    }
}

data class CategoryUiState(
    val isLoading: Boolean = true,
    val content: List<HorrorContent> = emptyList(),
    val category: ContentCategory = ContentCategory.HORROR,
    val errorMessage: String? = null
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: HorrorContentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun loadContentByCategory(category: ContentCategory) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true, 
                category = category,
                errorMessage = null
            )
            
            try {
                repository.getContentByCategory(category).collect { content ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        content = content
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

    fun refreshContent() {
        loadContentByCategory(_uiState.value.category)
    }
}

data class SearchUiState(
    val isLoading: Boolean = false,
    val searchResults: List<HorrorContent> = emptyList(),
    val query: String = "",
    val hasSearched: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: HorrorContentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun searchContent(query: String) {
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(
                searchResults = emptyList(),
                query = query,
                hasSearched = false
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                query = query,
                hasSearched = true,
                errorMessage = null
            )
            
            try {
                repository.searchContent(query).collect { results ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        searchResults = results
                    )
                }
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Search failed"
                )
            }
        }
    }

    fun clearSearch() {
        _uiState.value = SearchUiState()
    }
}