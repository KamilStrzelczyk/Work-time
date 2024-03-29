buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}