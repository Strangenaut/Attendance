buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("com.google.gms.google-services") version libs.versions.googleServices apply false
    id("com.google.dagger.hilt.android") version libs.versions.hilt.android.gradle.plugin apply false
}