import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.serdicagrid.serdicaweatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.serdicagrid.serdicaweatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Load `local.properties` for secure API key access
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())

            val googleMapsApiKey = localProperties.getProperty("GOOGLE_MAPS_API_KEY") ?: ""
            val openWeatherMapApiKey = localProperties.getProperty("OPEN_WEATHER_MAP_API_KEY") ?: ""

            buildConfigField("String", "GOOGLE_MAPS_API_KEY", "\"$googleMapsApiKey\"")
            buildConfigField("String", "OPEN_WEATHER_MAP_API_KEY", "\"$openWeatherMapApiKey\"")

            manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = googleMapsApiKey
            manifestPlaceholders["OPEN_WEATHER_MAP_API_KEY"] = openWeatherMapApiKey
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

        kotlinOptions {
            jvmTarget = "11"
        }

        buildFeatures {
            compose = true
            buildConfig = true
        }
    }

    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)

        implementation(libs.play.services.location)
        implementation(libs.accompanist.permissions)
        implementation(libs.google.maps.compose)
        implementation(libs.play.services.maps)
    }
}
