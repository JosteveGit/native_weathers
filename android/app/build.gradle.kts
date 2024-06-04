plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.weather.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.weather.app"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.kotlin.stdlib)

    // AndroidX and Jetpack Compose dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui.v147)
    implementation(libs.androidx.material.v147)
    implementation(libs.androidx.ui.tooling.preview.v147)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose.v253)

    // Koin dependencies for dependency injection
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
//    implementation(libs.koin.androidx.viewmodel)

    // Retrofit dependencies for network requests
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Coroutine dependencies for background operations
    implementation(libs.kotlinx.coroutines.core.v172)
    implementation(libs.kotlinx.coroutines.android.v172)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.material3.android)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}