package com.cinematichororuniverse.dmsplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cinematichororuniverse.dmsplus.data.model.Creator
import com.cinematichororuniverse.dmsplus.data.model.HorrorContent
import com.cinematichororuniverse.dmsplus.ui.components.HorrorContentListItem
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorColors
import com.cinematichororuniverse.dmsplus.ui.viewmodel.HomeViewModel

@Composable
fun ChannelScreen(
    onContentClick: (HorrorContent) -> Unit,
    onCreatorClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Horror Channels",
                style = MaterialTheme.typography.headlineMedium,
                color = HorrorColors.GhostWhite,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Discover the best horror content creators",
                style = MaterialTheme.typography.bodyMedium,
                color = HorrorColors.GhostWhite.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Featured Creators
            uiState.creators.forEach { creator ->
                CreatorChannelCard(
                    creator = creator,
                    onCreatorClick = onCreatorClick,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun CreatorChannelCard(
    creator: Creator,
    onCreatorClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onCreatorClick(creator.id) },
        colors = CardDefaults.cardColors(
            containerColor = HorrorColors.ShadowGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar placeholder
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            HorrorColors.DarkGray,
                            androidx.compose.foundation.shape.CircleShape
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

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = creator.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        color = HorrorColors.GhostWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${creator.subscriberCount / 1000}K subscribers",
                        style = MaterialTheme.typography.bodySmall,
                        color = HorrorColors.CrimsonAccent
                    )

                    if (creator.isVerified) {
                        Text(
                            text = "✓ Verified Creator",
                            style = MaterialTheme.typography.bodySmall,
                            color = HorrorColors.BloodRed
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = creator.description,
                style = MaterialTheme.typography.bodySmall,
                color = HorrorColors.GhostWhite.copy(alpha = 0.8f),
                maxLines = 2
            )
        }
    }
}