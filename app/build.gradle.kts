plugins {
    alias(libs.plugins.android.application)

    id("com.google.gms.google-services")
    id ("androidx.navigation.safeargs")

}

android {
    namespace = "com.example.mealsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mealsapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation ("com.google.firebase:firebase-auth")

    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    //firestore
    implementation("com.google.firebase:firebase-firestore")

    implementation ("com.google.android.material:material:<version>")
    //lottie
    implementation ("com.airbnb.android:lottie:6.1.0")
    //Navigation Componant
    implementation (libs.navigation.fragment)
    implementation (libs.androidx.navigation.ui)

    //Room
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    //RxJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.5")
    //RXJava with retrofit
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    //RxJava with room
    implementation ("androidx.room:room-rxjava3:2.6.1")

    //carousel
    implementation ("com.github.sparrow007:carouselrecyclerview:1.2.6")

}