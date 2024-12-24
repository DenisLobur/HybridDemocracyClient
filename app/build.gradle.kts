plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.hybriddemocracy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hybriddemocracy"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation("androidx.work:work-runtime:2.10.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.security:security-crypto:1.0.0")

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation(libs.androidx.navigation.compose)

    // Additional libraries
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.hilt:hilt-work:1.2.0")
//    kapt('androidx.hilt:hilt-compiler:1.2.0')

//    implementation(libs.hilt.android)
//    implementation(libs.androidx.hilt.navigation.compose)
//    ksp(libs.hilt.compiler)
}
//
//ksp {
//    arg("room.schemaLocation", "$projectDir/schemas")
//}