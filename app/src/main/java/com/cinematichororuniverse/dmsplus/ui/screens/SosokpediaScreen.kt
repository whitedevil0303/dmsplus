package com.cinematichororuniverse.dmsplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorColors

data class SosokHoror(
    val id: String,
    val nama: String,
    val asal: String,
    val deskripsi: String,
    val kategori: String,
    val tingkatBahaya: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SosokpediaScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    
    val sosokHororList = remember {
        listOf(
            SosokHoror(
                id = "1",
                nama = "Pocong",
                asal = "Indonesia",
                deskripsi = "Hantu yang terbungkus kain kafan, biasanya muncul karena ikatan kain kafan yang tidak dilepas setelah kematian.",
                kategori = "Hantu Tradisional",
                tingkatBahaya = "Sedang"
            ),
            SosokHoror(
                id = "2", 
                nama = "Kuntilanak",
                asal = "Indonesia/Malaysia",
                deskripsi = "Hantu wanita yang meninggal saat melahirkan, sering mengganggu pria dan bayi.",
                kategori = "Hantu Tradisional",
                tingkatBahaya = "Tinggi"
            ),
            SosokHoror(
                id = "3",
                nama = "Genderuwo",
                asal = "Jawa",
                deskripsi = "Makhluk halus berbadan besar dan berbulu, sering mengganggu wanita.",
                kategori = "Jin/Setan",
                tingkatBahaya = "Tinggi"
            ),
            SosokHoror(
                id = "4",
                nama = "Tuyul",
                asal = "Indonesia",
                deskripsi = "Makhluk halus kecil yang dipelihara untuk mencuri uang.",
                kategori = "Jin Peliharaan",
                tingkatBahaya = "Rendah"
            ),
            SosokHoror(
                id = "5",
                nama = "Wewe Gombel",
                asal = "Jawa Tengah",
                deskripsi = "Hantu wanita tua yang menculik anak-anak nakal untuk dididik.",
                kategori = "Hantu Tradisional",
                tingkatBahaya = "Sedang"
            ),
            SosokHoror(
                id = "6",
                nama = "Leak",
                asal = "Bali",
                deskripsi = "Praktisi ilmu hitam yang dapat mengubah wujud menjadi berbagai bentuk menakutkan.",
                kategori = "Ilmu Hitam",
                tingkatBahaya = "Sangat Tinggi"
            )
        )
    }
    
    val filteredSosok = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            sosokHororList
        } else {
            sosokHororList.filter { 
                it.nama.contains(searchQuery, ignoreCase = true) ||
                it.asal.contains(searchQuery, ignoreCase = true) ||
                it.kategori.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        HorrorColors.DeepBlack,
                        HorrorColors.MidnightBlue.copy(alpha = 0.3f),
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
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = "Sosokpedia",
                    tint = HorrorColors.BloodRed,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = "Sosokpedia",
                        style = MaterialTheme.typography.headlineMedium,
                        color = HorrorColors.GhostWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Ensiklopedia Makhluk Horor Indonesia",
                        style = MaterialTheme.typography.bodyMedium,
                        color = HorrorColors.GhostWhite.copy(alpha = 0.7f)
                    )
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = {
                    Text(
                        text = "Cari sosok horor...",
                        color = HorrorColors.GhostWhite.copy(alpha = 0.6f)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = HorrorColors.CrimsonAccent
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = HorrorColors.BloodRed,
                    unfocusedBorderColor = HorrorColors.DarkGray,
                    focusedTextColor = HorrorColors.GhostWhite,
                    unfocusedTextColor = HorrorColors.GhostWhite,
                    cursorColor = HorrorColors.BloodRed
                ),
                singleLine = true
            )

            // Content
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredSosok) { sosok ->
                    SosokCard(sosok = sosok)
                }
                
                if (filteredSosok.isEmpty() && searchQuery.isNotBlank()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Tidak ada sosok yang ditemukan",
                                style = MaterialTheme.typography.bodyLarge,
                                color = HorrorColors.GhostWhite.copy(alpha = 0.6f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SosokCard(
    sosok: SosokHoror,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = HorrorColors.ShadowGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = sosok.nama,
                        style = MaterialTheme.typography.titleLarge,
                        color = HorrorColors.GhostWhite,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = "Asal: ${sosok.asal}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = HorrorColors.CrimsonAccent,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                Surface(
                    color = getBahayaColor(sosok.tingkatBahaya),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = sosok.tingkatBahaya,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Surface(
                color = HorrorColors.DarkGray,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = sosok.kategori,
                    style = MaterialTheme.typography.labelMedium,
                    color = HorrorColors.GhostWhite,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = sosok.deskripsi,
                style = MaterialTheme.typography.bodyMedium,
                color = HorrorColors.GhostWhite.copy(alpha = 0.8f),
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )
        }
    }
}

private fun getBahayaColor(tingkatBahaya: String): Color {
    return when (tingkatBahaya) {
        "Rendah" -> Color(0xFF4CAF50)
        "Sedang" -> Color(0xFFFF9800)
        "Tinggi" -> Color(0xFFFF5722)
        "Sangat Tinggi" -> Color(0xFF8B0000)
        else -> Color(0xFF757575)
    }
}