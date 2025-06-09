package com.cinematichororuniverse.dmsplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.cinematichororuniverse.dmsplus.navigation.DMSPlusNavigation
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorColors
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            HorrorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = HorrorColors.DeepBlack
                ) {
                    DMSPlusApp()
                }
            }
        }
    }
}

@Composable
fun DMSPlusApp() {
    DMSPlusNavigation()
}

@Preview(showBackground = true)
@Composable
fun DMSPlusAppPreview() {
    HorrorTheme {
        DMSPlusApp()
    }
}