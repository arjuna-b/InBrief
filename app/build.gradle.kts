plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
//    alias(libs.plugins.compose.compiler)
    kotlin("kapt")
}



android {
    namespace = "com.arjuna.inbrief"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.arjuna.inbrief"
        minSdk = 24
        targetSdk = 35

        buildConfigField("String", "API_KEY", "\"KEEP_YOUR_APIKEY_HERE\"")

        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.core.splashscreen)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.navigation.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.hilt.android)
    implementation(libs.androidx.junit.ktx)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.compose)
    testImplementation(kotlin("test"))

    // Coil core
    implementation(libs.coil) // latest stable

    // Optional: for Compose support
    implementation(libs.coil.compose)

    //extended icons
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)

    //room
    implementation (libs.androidx.room.runtime)
    kapt ("androidx.room:room-compiler:2.6.1")

    // Room + Coroutines
    implementation (libs.androidx.room.ktx)

}
