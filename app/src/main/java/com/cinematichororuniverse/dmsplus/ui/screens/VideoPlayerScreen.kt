package com.cinematichororuniverse.dmsplus.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cinematichororuniverse.dmsplus.data.model.HorrorContent
import com.cinematichororuniverse.dmsplus.ui.components.HLSVideoPlayer
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorColors
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    content: HorrorContent,
    onBackClick: () -> Unit,
    onRelatedContentClick: (HorrorContent) -> Unit,
    relatedContent: List<HorrorContent> = emptyList(),
    modifier: Modifier = Modifier
) {
    var isFullscreen by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    BackHandler {
        if (isFullscreen) {
            isFullscreen = false
        } else {
            onBackClick()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HorrorColors.DeepBlack)
    ) {
        if (isFullscreen || isLandscape) {
            // Fullscreen video player
            HLSVideoPlayer(
                hlsUrl = content.hlsUrl,
                modifier = Modifier.fillMaxSize(),
                autoPlay = true
            )
            
            // Fullscreen controls overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = { 
                        if (isFullscreen) {
                            isFullscreen = false
                        } else {
                            onBackClick()
                        }
                    },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        } else {
            // Portrait mode with content details
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Video Player Section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                ) {
                    HLSVideoPlayer(
                        hlsUrl = content.hlsUrl,
                        modifier = Modifier.fillMaxSize(),
                        autoPlay = true,
                        onPlayerReady = { player ->
                            // Handle player ready if needed
                        }
                    )
                    
                    // Back button overlay
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                // Content Details Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Title and Creator
                    Text(
                        text = content.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = HorrorColors.GhostWhite,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = content.creatorName,
                                style = MaterialTheme.typography.titleMedium,
                                color = HorrorColors.CrimsonAccent,
                                fontWeight = FontWeight.Medium
                            )
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = HorrorColors.CrimsonAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = String.format("%.1f", content.rating),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = HorrorColors.GhostWhite
                                    )
                                }
                                
                                Text(
                                    text = "${content.viewCount / 1000}K views",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = HorrorColors.GhostWhite.copy(alpha = 0.7f)
                                )
                                
                                Text(
                                    text = formatDate(content.releaseDate),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = HorrorColors.GhostWhite.copy(alpha = 0.7f)
                                )
                            }
                        }
                        
                        // Action buttons
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = { isFavorite = !isFavorite }
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite) HorrorColors.BloodRed else HorrorColors.GhostWhite
                                )
                            }
                            
                            IconButton(
                                onClick = { /* Handle share */ }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = HorrorColors.GhostWhite
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Badges
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (content.isExclusive) {
                            Badge(
                                text = "EXCLUSIVE",
                                backgroundColor = HorrorColors.BloodRed
                            )
                        }
                        if (content.isPremium) {
                            Badge(
                                text = "PREMIUM",
                                backgroundColor = HorrorColors.CrimsonAccent
                            )
                        }
                        Badge(
                            text = content.category.name,
                            backgroundColor = HorrorColors.DarkGray
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Description
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        color = HorrorColors.GhostWhite,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = content.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = HorrorColors.GhostWhite.copy(alpha = 0.8f),
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Tags
                    if (content.tags.isNotEmpty()) {
                        Text(
                            text = "Tags",
                            style = MaterialTheme.typography.titleMedium,
                            color = HorrorColors.GhostWhite,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            content.tags.forEach { tag ->
                                Surface(
                                    color = HorrorColors.ShadowGray,
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        text = "#$tag",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = HorrorColors.CrimsonAccent,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    
                    // Related Content
                    if (relatedContent.isNotEmpty()) {
                        Text(
                            text = "Related Content",
                            style = MaterialTheme.typography.titleMedium,
                            color = HorrorColors.GhostWhite,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            relatedContent.take(5).forEach { relatedItem ->
                                RelatedContentItem(
                                    content = relatedItem,
                                    onClick = { onRelatedContentClick(relatedItem) }
                                )
                            }
                        }
                    }
                }
                
                // Bottom spacing for navigation
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun Badge(
    text: String,
    backgroundColor: Color
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun RelatedContentItem(
    content: HorrorContent,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = HorrorColors.ShadowGray
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            // Thumbnail
            AsyncImage(
                model = content.thumbnailUrl,
                contentDescription = content.title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Content info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = content.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = HorrorColors.GhostWhite,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Text(
                    text = content.creatorName,
                    style = MaterialTheme.typography.bodySmall,
                    color = HorrorColors.CrimsonAccent,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatDuration(content.duration),
                        style = MaterialTheme.typography.labelSmall,
                        color = HorrorColors.GhostWhite.copy(alpha = 0.7f)
                    )
                    
                    Text(
                        text = "${content.viewCount / 1000}K views",
                        style = MaterialTheme.typography.labelSmall,
                        color = HorrorColors.GhostWhite.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

private fun formatDuration(durationMs: Long): String {
    val duration = durationMs.milliseconds
    val hours = duration.inWholeHours
    val minutes = (duration.inWholeMinutes % 60)
    val seconds = (duration.inWholeSeconds % 60)
    
    return if (hours > 0) {
        String.format("%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%d:%02d", minutes, seconds)
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}