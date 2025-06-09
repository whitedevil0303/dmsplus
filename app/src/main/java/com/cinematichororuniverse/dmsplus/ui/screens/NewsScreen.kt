package com.cinematichororuniverse.dmsplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorColors
import java.text.SimpleDateFormat
import java.util.*

data class NewsArticle(
    val id: String,
    val title: String,
    val content: String,
    val author: String,
    val publishDate: Long,
    val imageUrl: String,
    val category: String,
    val isBreaking: Boolean = false
)

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier
) {
    val newsArticles = remember {
        listOf(
            NewsArticle(
                id = "1",
                title = "Diary Misteri Sara Rilis Konten Eksklusif Terbaru",
                content = "Creator terkenal Diary Misteri Sara mengumumkan peluncuran series horor eksklusif yang akan tayang perdana di DMS+ minggu depan. Series ini mengangkat kisah nyata dari daerah Jawa Barat yang telah diteliti selama 6 bulan.",
                author = "Tim Redaksi DMS+",
                publishDate = System.currentTimeMillis() - 3600000, // 1 hour ago
                imageUrl = "https://example.com/news1.jpg",
                category = "Creator Update",
                isBreaking = true
            ),
            NewsArticle(
                id = "2",
                title = "Film Horor Indonesia Raih Penghargaan di Festival Internasional",
                content = "Film horor Indonesia 'Danur 4' berhasil meraih penghargaan Best Horror Film di Asian Horror Film Festival 2024. Pencapaian membanggakan ini semakin mengukuhkan posisi sinema horor Indonesia di kancah internasional.",
                author = "Sarah Wijaya",
                publishDate = System.currentTimeMillis() - 86400000, // 1 day ago
                imageUrl = "https://example.com/news2.jpg",
                category = "Film Indonesia"
            ),
            NewsArticle(
                id = "3",
                title = "Lokasi Syuting Horor Paling Angker di Indonesia",
                content = "Berbagai lokasi syuting film horor Indonesia ternyata memiliki sejarah kelam yang nyata. Dari rumah tua di Bandung hingga bekas rumah sakit di Jakarta, lokasi-lokasi ini memberikan atmosfer autentik untuk produksi horor.",
                author = "Budi Santoso",
                publishDate = System.currentTimeMillis() - 172800000, // 2 days ago
                imageUrl = "https://example.com/news3.jpg",
                category = "Behind The Scene"
            ),
            NewsArticle(
                id = "4",
                title = "Tips Menghadapi Ketakutan Saat Menonton Film Horor",
                content = "Psikolog menjelaskan teknik-teknik untuk mengelola ketakutan saat menonton konten horor. Mulai dari pernapasan hingga mindset yang tepat, simak tips lengkapnya untuk pengalaman menonton yang lebih nyaman.",
                author = "Dr. Maya Sari",
                publishDate = System.currentTimeMillis() - 259200000, // 3 days ago
                imageUrl = "https://example.com/news4.jpg",
                category = "Tips & Trik"
            ),
            NewsArticle(
                id = "5",
                title = "Sejarah Hantu Pocong dalam Budaya Indonesia",
                content = "Menelisik asal-usul kepercayaan terhadap hantu pocong dalam budaya Nusantara. Dari ritual pemakaman hingga pengaruhnya dalam film horor modern, pocong tetap menjadi ikon horor Indonesia yang tak lekang waktu.",
                author = "Prof. Ahmad Rahman",
                publishDate = System.currentTimeMillis() - 345600000, // 4 days ago
                imageUrl = "https://example.com/news5.jpg",
                category = "Budaya"
            )
        )
    }

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
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Newspaper,
                    contentDescription = "News",
                    tint = HorrorColors.BloodRed,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = "Horror News",
                        style = MaterialTheme.typography.headlineMedium,
                        color = HorrorColors.GhostWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Berita Terkini Dunia Horor Indonesia",
                        style = MaterialTheme.typography.bodyMedium,
                        color = HorrorColors.GhostWhite.copy(alpha = 0.7f)
                    )
                }
            }

            // News List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(newsArticles) { article ->
                    NewsCard(
                        article = article,
                        onClick = { /* Handle article click */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun NewsCard(
    article: NewsArticle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = HorrorColors.ShadowGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Article Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = article.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Breaking News Badge
                if (article.isBreaking) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp),
                        color = HorrorColors.BloodRed,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "BREAKING",
                            style = MaterialTheme.typography.labelSmall,
                            color = HorrorColors.GhostWhite,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                
                // Category Badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp),
                    color = HorrorColors.DarkGray.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = article.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = HorrorColors.GhostWhite,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            // Article Content
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = HorrorColors.GhostWhite,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = HorrorColors.GhostWhite.copy(alpha = 0.8f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Article Meta
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Author",
                            tint = HorrorColors.CrimsonAccent,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = article.author,
                            style = MaterialTheme.typography.bodySmall,
                            color = HorrorColors.CrimsonAccent
                        )
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date",
                            tint = HorrorColors.GhostWhite.copy(alpha = 0.6f),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = formatNewsDate(article.publishDate),
                            style = MaterialTheme.typography.bodySmall,
                            color = HorrorColors.GhostWhite.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}

private fun formatNewsDate(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 3600000 -> { // Less than 1 hour
            val minutes = diff / 60000
            "${minutes}m ago"
        }
        diff < 86400000 -> { // Less than 1 day
            val hours = diff / 3600000
            "${hours}h ago"
        }
        diff < 604800000 -> { // Less than 1 week
            val days = diff / 86400000
            "${days}d ago"
        }
        else -> {
            val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}