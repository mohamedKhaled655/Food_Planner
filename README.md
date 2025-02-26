# Food Planner Application


## 📱 Overview

Food Planner is an Android application that helps users plan their weekly meals with ease. Using TheMealDB API, the app provides an extensive collection of recipes from around the world, allowing users to discover, save, and organize meals according to their preferences.



## ✨ Features

### 🍽️ Meal Discovery
- **Meal of the Day**: Daily inspiration with a featured meal
- **Category Browsing**: Explore meals organized by food categories
- **Country-based Exploration**: Discover popular dishes from different cuisines
- **Ingredient Search**: Find meals that include specific ingredients

### 📋 Planning & Organization
- **Weekly Meal Planner**: Schedule meals for each day of the week
- **Favorites Collection**: Save and organize preferred recipes
- **Offline Access**: View favorite meals and weekly plans without internet

### 👤 User Experience
- **Multiple Authentication Options**: Email, Google login
- **Guest Mode**: Browse categories and search meals without registration
- **Data Synchronization**: Backup favorites and meal plans to the cloud
- **Detailed Recipe Cards**: Complete with ingredients, steps, and video tutorials

## 🛠️ Tech Stack

- **Architecture Pattern**: MVP (Model-View-Presenter)
- **Reactive Programming**: RxJava
- **Local Storage**: Room Database
- **Remote Data**: Retrofit with TheMealDB API
- **Authentication & Sync**: Firebase Authentication and Realtime Database
- **Image Loading**: Glide
- **Animations**: Lottie


## 📝 Implementation Details

### API Integration
The application uses TheMealDB API (https://themealdb.com/api.php) to fetch meal data including:
- Random meals for the "Meal of the Day" feature
- Categorized meals listing
- Country-based meal collections
- Detailed recipe information with ingredients and instructions

### Offline Functionality
- Room Database stores favorite meals and weekly plans
- SharedPreferences manages user session and app settings

### Authentication Flow
- Firebase Authentication for user management
- Persistent login using Firebase
- Guest mode with limited functionality

## 📲 Installation

1. Clone this repository
   ```
   git clone https://github.com/mohamedKhaled655/Food_Planner.git
   ```
2. Open the project in Android Studio
3. Configure Firebase:
   - Create a Firebase project
   - Add your Android app to the Firebase project
   - Download the `google-services.json` file and place it in the app directory
4. Build and run the application

## 🔧 Configuration

To configure the application, modify the following files:
- `app/src/main/res/values/config.xml` - API endpoints and general settings
- `app/build.gradle` - Dependencies and build configuration
- `app/google-services.json` - Firebase configuration

## 📋 Requirements
- Active internet connection for initial data loading

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgements

- [TheMealDB](https://themealdb.com/) for providing the API
- [Firebase](https://firebase.google.com/) for authentication and database services
- [Lottie](https://airbnb.design/lottie/) for beautiful animations
