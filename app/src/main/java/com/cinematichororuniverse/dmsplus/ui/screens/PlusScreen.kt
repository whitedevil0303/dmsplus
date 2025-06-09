package com.cinematichororuniverse.dmsplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
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
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorColors
import com.cinematichororuniverse.dmsplus.ui.viewmodel.HomeViewModel

@Composable
fun PlusScreen(
    onContentClick: (HorrorContent) -> Unit,
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
                        HorrorColors.DarkRed.copy(alpha = 0.3f),
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
            // Premium Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = HorrorColors.BloodRed
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Premium",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "DMS+ Premium",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    
                    Text(
                        text = "Unlock Exclusive Horror Content",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Premium Benefits
            Text(
                text = "Premium Benefits",
                style = MaterialTheme.typography.headlineSmall,
                color = HorrorColors.GhostWhite,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val benefits = listOf(
                "Exclusive horror content from top creators",
                "Ad-free viewing experience",
                "Early access to new releases",
                "Download content for offline viewing",
                "Premium-only live streams",
                "HD and 4K video quality"
            )

            benefits.forEach { benefit ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Benefit",
                        tint = HorrorColors.BloodRed,
                        modifier = Modifier.size(24.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Text(
                        text = benefit,
                        style = MaterialTheme.typography.bodyMedium,
                        color = HorrorColors.GhostWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Subscription Button
            Button(
                onClick = { /* Handle subscription */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HorrorColors.BloodRed
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Upgrade to Premium - IDR 49,000/month",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Exclusive Content Preview
            Text(
                text = "Exclusive Content",
                style = MaterialTheme.typography.headlineSmall,
                color = HorrorColors.GhostWhite,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Get access to premium horror content",
                style = MaterialTheme.typography.bodyMedium,
                color = HorrorColors.GhostWhite.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Show exclusive content
            uiState.exclusiveContent.forEach { content ->
                HorrorContentCard(
                    content = content,
                    onClick = { onContentClick(content) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
            }
        }
    }
}