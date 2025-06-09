package com.cinematichororuniverse.dmsplus.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cinematichororuniverse.dmsplus.data.model.HorrorContent
import com.cinematichororuniverse.dmsplus.ui.screens.*
import com.cinematichororuniverse.dmsplus.ui.theme.HorrorColors

// Navigation routes
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Channel : Screen("channel", "Channel", Icons.Outlined.PlayCircle)
    object Plus : Screen("plus", "Plus", Icons.Default.Star)
    object Sosokpedia : Screen("sosokpedia", "Sosokpedia", Icons.Default.MenuBook)
    object News : Screen("news", "News", Icons.Default.Newspaper)
    object VideoPlayer : Screen("video_player/{contentId}", "Video Player", Icons.Default.PlayArrow) {
        fun createRoute(contentId: String) = "video_player/$contentId"
    }
    object Creator : Screen("creator/{creatorId}", "Creator", Icons.Default.Person) {
        fun createRoute(creatorId: String) = "creator/$creatorId"
    }
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Channel,
    Screen.Plus,
    Screen.Sosokpedia,
    Screen.News
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DMSPlusNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // Check if current route should show bottom navigation
    val showBottomNav = bottomNavItems.any {
        currentDestination?.hierarchy?.any { dest ->
            dest.route == it.route
        } == true
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (showBottomNav) {
                TopAppBar(
                    title = {
                        Text(
                            text = "DMS+",
                            color = HorrorColors.BloodRed,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { /* Handle profile click */ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile",
                                tint = HorrorColors.GhostWhite
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = HorrorColors.DeepBlack,
                        titleContentColor = HorrorColors.BloodRed,
                        actionIconContentColor = HorrorColors.GhostWhite
                    )
                )
            }
        },
        bottomBar = {
            if (showBottomNav) {
                NavigationBar(
                    containerColor = HorrorColors.ShadowGray,
                    contentColor = HorrorColors.GhostWhite
                ) {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.title
                                )
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            selected = currentDestination?.hierarchy?.any {
                                it.route == screen.route
                            } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = HorrorColors.BloodRed,
                                selectedTextColor = HorrorColors.BloodRed,
                                unselectedIconColor = HorrorColors.GhostWhite.copy(alpha = 0.6f),
                                unselectedTextColor = HorrorColors.GhostWhite.copy(alpha = 0.6f),
                                indicatorColor = HorrorColors.DarkRed
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onContentClick = { content ->
                        navController.navigate(Screen.VideoPlayer.createRoute(content.id))
                    },
                    onCreatorClick = { creatorId ->
                        navController.navigate(Screen.Creator.createRoute(creatorId))
                    }
                )
            }
            
            composable(Screen.Channel.route) {
                ChannelScreen(
                    onContentClick = { content ->
                        navController.navigate(Screen.VideoPlayer.createRoute(content.id))
                    },
                    onCreatorClick = { creatorId ->
                        navController.navigate(Screen.Creator.createRoute(creatorId))
                    }
                )
            }
            
            composable(Screen.Plus.route) {
                PlusScreen(
                    onContentClick = { content ->
                        navController.navigate(Screen.VideoPlayer.createRoute(content.id))
                    }
                )
            }
            
            composable(Screen.Sosokpedia.route) {
                SosokpediaScreen()
            }
            
            composable(Screen.News.route) {
                NewsScreen()
            }
            
            composable(Screen.VideoPlayer.route) { backStackEntry ->
                val contentId = backStackEntry.arguments?.getString("contentId") ?: ""
                
                // In a real app, you would fetch the content by ID from your repository
                // For now, we'll use mock data
                val mockContent = HorrorContent(
                    id = contentId,
                    title = "Sample Horror Content",
                    description = "This is a sample horror content description that would normally be fetched from your data source.",
                    thumbnailUrl = "https://example.com/thumbnail.jpg",
                    hlsUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", // Sample HLS URL
                    duration = 1800000,
                    creatorName = "Diary Misteri Sara",
                    creatorAvatar = "https://example.com/avatar.jpg",
                    category = com.cinematichororuniverse.dmsplus.data.model.ContentCategory.HORROR,
                    tags = listOf("horror", "misteri", "seram"),
                    rating = 4.5f,
                    viewCount = 125000,
                    releaseDate = System.currentTimeMillis(),
                    isExclusive = true
                )
                
                VideoPlayerScreen(
                    content = mockContent,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onRelatedContentClick = { relatedContent ->
                        navController.navigate(Screen.VideoPlayer.createRoute(relatedContent.id))
                    }
                )
            }
        }
    }
}