// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.kotlin.android) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.6.10" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.firebase.crashlytics") version "3.0.5" apply false
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) apply false
}