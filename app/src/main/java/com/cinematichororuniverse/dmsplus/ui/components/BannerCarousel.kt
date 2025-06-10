package com.cinematichororuniverse.dmsplus.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cinematichororuniverse.dmsplus.data.model.Banner
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorColors
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerCarousel(
    banners: List<Banner>,
    onBannerClick: (Banner) -> Unit,
    modifier: Modifier = Modifier,
    autoScrollDuration: Long = 4000L
) {
    if (banners.isEmpty()) return
    
    // Use a large but reasonable number for infinite effect
    val loopCount = 1000
    val actualStartIndex = (loopCount / 2) * banners.size
    
    val pagerState = rememberPagerState(
        initialPage = actualStartIndex,
        pageCount = { banners.size * loopCount }
    )
    
    // Auto scroll effect
    LaunchedEffect(Unit) {
        while (true) {
            delay(autoScrollDuration)
            if (!pagerState.isScrollInProgress) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }
    
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                flingBehavior = PagerDefaults.flingBehavior(state = pagerState)
            ) { page ->
                val actualIndex = page % banners.size
                val banner = banners[actualIndex]
                BannerCard(
                    banner = banner,
                    onClick = { onBannerClick(banner) },
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            // Page indicators
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(banners.size) { iteration ->
                    val actualCurrentIndex = pagerState.currentPage % banners.size
                    val color = if (actualCurrentIndex == iteration) {
                        HorrorColors.BloodRed
                    } else {
                        HorrorColors.GhostWhite.copy(alpha = 0.5f)
                    }
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BannerCard(
    banner: Banner,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = banner.image,
                contentDescription = banner.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Gradient overlay for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            
            // Banner content
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                if (banner.title.isNotEmpty()) {
                    Text(
                        text = banner.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = HorrorColors.GhostWhite,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                if (banner.caption.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = banner.caption,
                        style = MaterialTheme.typography.bodyMedium,
                        color = HorrorColors.GhostWhite.copy(alpha = 0.9f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}