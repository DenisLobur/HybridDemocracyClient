// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

//    alias(libs.plugins.ksp) apply false
//    alias(libs.plugins.hilt.android) apply false
//    alias(libs.plugins.ksp) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
//        classpath("com.google.dagger:hilt-android-gradle-plugin:2.53.1")
        classpath("com.android.tools.build:gradle:8.7.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
        classpath("org.jetbrains.kotlin:kotlin-serialization:2.0.21")
        classpath("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.0.21")
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.6")
        classpath("com.google.android.gms:strict-version-matcher-plugin:1.2.4")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.0.21-1.0.25")
        classpath("com.google.firebase:firebase-appdistribution-gradle:5.0.0")
    }
}