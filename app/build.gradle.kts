import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose.compiler)
    id("kotlin-kapt")
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

android {
    namespace = "com.example.finalproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.finalproject"
        minSdk = 24
        targetSdk = 35
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
        isCoreLibraryDesugaringEnabled = true
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
    buildFeatures {
        buildConfig = true
        buildTypes {
            debug {
                buildConfigField("String", "BASE_URL",
                    "\"https://67ee8693c11d5ff4bf79ebdf.mockapi.io/final/\"")
                buildConfigField("String", "IMGBB_API_KEY",
                    localProperties.getProperty("AMADEUS_API_KEY"))
                buildConfigField("String", "IMGBB_BASE_URL",
                    "\"https://api.imgbb.com/\"")
                buildConfigField("String", "BASE_URL_FOR_TOKEN",
                    "\"https://test.api.amadeus.com/\"")
                buildConfigField("String", "AMADEUS_API_KEY",
                    localProperties.getProperty("AMADEUS_API_KEY")
                )
                buildConfigField("String", "AMADEUS_API_SECRET",
                    localProperties.getProperty("AMADEUS_API_SECRET")
                )
                buildConfigField("String", "GOOGLE_PLACES_API_KEY",
                    localProperties.getProperty("GOOGLE_PLACES_API_KEY"))
            }
            release {
                buildConfigField("String", "BASE_URL", "\"https://67ee8693c11d5ff4bf79ebdf.mockapi.io/final/\"")

            }
        }
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
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    implementation (libs.converter.gson)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.androidx.material.icons.extended)
    implementation(libs.serialization)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.coil.compose)

    implementation("androidx.datastore:datastore-preferences:1.1.0")

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.junit.jupiter)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    testImplementation(libs.truth)

    coreLibraryDesugaring(libs.desugar.jdk.libs)
   // implementation (libs.places)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kapt {
    correctErrorTypes = true
}
