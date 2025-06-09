package com.cinematichororuniverse.dmsplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cinematichororuniverse.dmsplus.data.model.HorrorContent
import com.cinematichororuniverse.dmsplus.ui.components.HorrorContentCard
import com.cinematichororuniverse.dmsplus.ui.components.HorrorContentListItem
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorColors
import com.cinematichororuniverse.dmsplus.ui.viewmodel.HomeViewModel
import com.cinematichororuniverse.dmsplus.ui.viewmodel.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onContentClick: (HorrorContent) -> Unit,
    onCreatorClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        HorrorColors.DeepBlack,
                        HorrorColors.ShadowGray.copy(alpha = 0.8f),
                        HorrorColors.DeepBlack
                    )
                )
            )
    ) {
        when {
            uiState.isLoading -> {
                LoadingContent()
            }
            uiState.errorMessage != null -> {
                val errorMessage = uiState.errorMessage
                ErrorContent(
                    errorMessage = errorMessage ?: "Unknown error occurred",
                    onRetry = { viewModel.retryLoading() }
                )
            }
            else -> {
                HomeContent(
                    uiState = uiState,
                    onContentClick = onContentClick,
                    onCreatorClick = onCreatorClick,
                    onRefresh = { viewModel.refreshContent() },
                    scrollState = scrollState
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                color = HorrorColors.BloodRed,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "Loading horror content...",
                style = MaterialTheme.typography.bodyLarge,
                color = HorrorColors.GhostWhite,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ErrorContent(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.headlineSmall,
                color = HorrorColors.GhostWhite,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = HorrorColors.GhostWhite.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = HorrorColors.BloodRed
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Retry"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Retry")
            }
        }
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onContentClick: (HorrorContent) -> Unit,
    onCreatorClick: (String) -> Unit,
    onRefresh: () -> Unit,
    scrollState: androidx.compose.foundation.ScrollState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 16.dp)
    ) {
        // Featured Content Section
        if (uiState.featuredContent.isNotEmpty()) {
            ContentSection(
                title = "Featured Horror",
                subtitle = "Exclusive content from top creators",
                content = uiState.featuredContent,
                onContentClick = onContentClick,
                showAsList = false
            )
        }
        
        // Trending Content Section
        if (uiState.trendingContent.isNotEmpty()) {
            ContentSection(
                title = "Trending Now",
                subtitle = "Most watched horror content",
                content = uiState.trendingContent,
                onContentClick = onContentClick,
                showAsList = false
            )
        }
        
        // Exclusive Content Section
        if (uiState.exclusiveContent.isNotEmpty()) {
            ContentSection(
                title = "Exclusive Content",
                subtitle = "Premium horror experiences",
                content = uiState.exclusiveContent,
                onContentClick = onContentClick,
                showAsList = false
            )
        }
        
        // Recent Content Section
        if (uiState.recentContent.isNotEmpty()) {
            ContentSection(
                title = "Recently Added",
                subtitle = "Latest horror content",
                content = uiState.recentContent.take(5),
                onContentClick = onContentClick,
                showAsList = true
            )
        }
        
        // Creators Section
        if (uiState.creators.isNotEmpty()) {
            CreatorsSection(
                creators = uiState.creators,
                onCreatorClick = onCreatorClick
            )
        }
        
        // Footer spacing
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun ContentSection(
    title: String,
    subtitle: String,
    content: List<HorrorContent>,
    onContentClick: (HorrorContent) -> Unit,
    showAsList: Boolean = false
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Section Header
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = HorrorColors.GhostWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = HorrorColors.GhostWhite.copy(alpha = 0.6f)
            )
        }
        
        // Content List/Grid
        if (showAsList) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                content.forEach { horrorContent ->
                    HorrorContentListItem(
                        content = horrorContent,
                        onClick = { onContentClick(horrorContent) }
                    )
                }
            }
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(content) { horrorContent ->
                    HorrorContentCard(
                        content = horrorContent,
                        onClick = { onContentClick(horrorContent) },
                        modifier = Modifier.width(280.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CreatorsSection(
    creators: List<com.cinematichororuniverse.dmsplus.data.model.Creator>,
    onCreatorClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Featured Creators",
                style = MaterialTheme.typography.headlineSmall,
                color = HorrorColors.GhostWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Top horror content creators",
                style = MaterialTheme.typography.bodySmall,
                color = HorrorColors.GhostWhite.copy(alpha = 0.6f)
            )
        }
        
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(creators) { creator ->
                CreatorCard(
                    creator = creator,
                    onClick = { onCreatorClick(creator.id) }
                )
            }
        }
    }
}

@Composable
private fun CreatorCard(
    creator: com.cinematichororuniverse.dmsplus.data.model.Creator,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .wrapContentHeight(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = HorrorColors.ShadowGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Creator avatar placeholder
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        HorrorColors.DarkGray,
                        RoundedCornerShape(30.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = creator.displayName.take(2).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = HorrorColors.GhostWhite,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = creator.displayName,
                style = MaterialTheme.typography.titleSmall,
                color = HorrorColors.GhostWhite,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            
            Text(
                text = "${creator.subscriberCount / 1000}K subscribers",
                style = MaterialTheme.typography.bodySmall,
                color = HorrorColors.CrimsonAccent,
                textAlign = TextAlign.Center
            )
        }
    }
}