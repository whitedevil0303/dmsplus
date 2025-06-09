# DMS+ - Indonesia's First Horror Cinematic Universe Application

![DMS+ Logo](https://img.shields.io/badge/DMS+-Horror%20Universe-8B0000?style=for-the-badge&logo=android)

**DMS+** is Indonesia's premier horror content streaming application, featuring exclusive content from top Indonesian horror creators including "Diary Misteri Sara" and other prominent figures in the horror entertainment industry.

## 🎬 Features

### Core Features
- **HLS Video Streaming**: High-quality adaptive streaming with professional-grade video player
- **Horror-Themed UI**: Dark, atmospheric design optimized for horror content consumption
- **Exclusive Content**: Access to premium and exclusive horror content from top creators
- **Creator Profiles**: Dedicated sections for featured horror content creators
- **Content Categories**: Horror, Mystery, Documentary, Behind-the-Scenes, Live Streams
- **Search & Discovery**: Advanced search and content recommendation system

### Video Features
- **Adaptive Quality**: Automatic quality adjustment based on network conditions
- **Offline Downloads**: Save content for offline viewing (Premium feature)
- **Picture-in-Picture**: Continue watching while using other apps
- **Custom Controls**: Horror-themed video player interface
- **Subtitle Support**: Indonesian and English subtitle options

### User Experience
- **Dark Mode Optimized**: Designed for comfortable horror content viewing
- **Smooth Performance**: Optimized for Android devices with efficient memory usage
- **Edge-to-Edge Design**: Modern Android UI with immersive experience
- **Responsive Design**: Adapts to different screen sizes and orientations

## 🏗️ Technical Architecture

### Technology Stack
- **Platform**: Android (Kotlin)
- **UI Framework**: Jetpack Compose
- **Architecture**: Clean Architecture + MVVM
- **Dependency Injection**: Hilt
- **Video Player**: ExoPlayer with HLS support
- **Navigation**: Navigation Compose
- **State Management**: StateFlow + Compose State
- **Image Loading**: Coil

### Project Structure
```
app/
├── src/main/java/com/cinematichororuniverse/dmsplus/
│   ├── data/
│   │   ├── model/          # Data models (HorrorContent, Creator, etc.)
│   │   └── repository/     # Data repositories
│   ├── di/                 # Dependency injection modules
│   ├── navigation/         # Navigation setup
│   └── ui/
│       ├── components/     # Reusable UI components
│       ├── screens/        # Screen composables
│       ├── theme/          # Horror-themed design system
│       └── viewmodel/      # ViewModels for state management
```

### Key Components

#### HLS Video Player
- Custom ExoPlayer implementation optimized for HLS streaming
- Automatic quality adaptation based on network conditions
- Error handling and recovery mechanisms
- Memory-efficient segment caching

#### Horror Theme System
- Custom color palette with blood red, deep black, and ghost white
- Typography system using serif fonts for headers
- Dark-themed Material 3 components
- Atmospheric gradients and visual effects

#### Performance Optimizations
- Efficient image loading with Coil
- Lazy loading for content lists
- Memory management for video playback
- Background processing with coroutines

## 🎨 Design System

### Color Palette
- **Primary**: Blood Red (#8B0000)
- **Background**: Deep Black (#0D0D0D)
- **Surface**: Shadow Gray (#1A1A1A)
- **Text**: Ghost White (#F8F8FF)
- **Accent**: Crimson (#DC143C)

### Typography
- **Headers**: Serif fonts for dramatic effect
- **Body**: Sans-serif for readability
- **Emphasis**: Bold weights for important content

## 🚀 Getting Started

### Prerequisites
- Android Studio Flamingo or later
- Android SDK 21 or higher
- Kotlin 1.8+
- Gradle 8.0+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/your-org/dmsplus-android.git
cd dmsplus-android
```

2. Open the project in Android Studio

3. Build and run the project:
```bash
./gradlew assembleDebug
```

### Configuration

#### Video Streaming Setup
The app uses HLS (HTTP Live Streaming) for video content. Update the repository with your HLS endpoints:

```kotlin
// In HorrorContentRepository.kt
val mockHorrorContent = listOf(
    HorrorContent(
        hlsUrl = "https://your-cdn.com/video.m3u8", // Your HLS URL
        // ... other properties
    )
)
```

#### API Integration
Replace mock data with actual API calls in the repository layer:

```kotlin
@Singleton
class HorrorContentRepository @Inject constructor(
    private val apiService: HorrorContentApiService
) {
    suspend fun getFeaturedContent(): Flow<List<HorrorContent>> = flow {
        emit(apiService.getFeaturedContent())
    }
}
```

## 📱 App Screenshots

### Home Screen
- Featured horror content carousel
- Trending content sections
- Creator spotlights
- Recently added content

### Video Player
- Full-screen HLS video playback
- Custom horror-themed controls
- Content information and ratings
- Related content suggestions

### Dark Theme
- Optimized for low-light viewing
- Atmospheric visual design
- Blood red accent colors
- Professional typography

## 🔧 Development

### Adding New Features

1. **New Screen**: Create in `ui/screens/`
2. **New Component**: Add to `ui/components/`
3. **Data Model**: Define in `data/model/`
4. **Navigation**: Update `navigation/DMSPlusNavigation.kt`

### Testing
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Build Variants
- **Debug**: Development build with logging
- **Release**: Production build with optimizations

## 🎯 Roadmap

### Version 1.0
- [x] HLS video streaming
- [x] Horror-themed UI
- [x] Content browsing
- [x] Basic navigation
- [ ] User authentication
- [ ] Offline downloads

### Version 1.1
- [ ] Push notifications
- [ ] Social features (comments, ratings)
- [ ] Advanced search filters
- [ ] Creator interaction features

### Version 2.0
- [ ] Live streaming support
- [ ] Virtual reality content
- [ ] Premium subscription system
- [ ] Multi-language support

## 🤝 Contributing

We welcome contributions from the horror content and Android development community!

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🏢 About PT Kreatif Berkah Abadi

PT Kreatif Berkah Abadi is a startup focused on developing content and technology products in the entertainment and media industry, specifically targeting horror genre enthusiasts in Indonesia.

## 📞 Contact

- **Website**: [DMS+ Official](https://dmsplus.id)
- **Email**: dev@dmsplus.id
- **Instagram**: [@dmsplus_official](https://instagram.com/dmsplus_official)

---

**DMS+** - Bringing Indonesia's horror universe to your mobile device 📱👻