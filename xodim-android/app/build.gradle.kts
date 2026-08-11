plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "uz.sunnat.sartarosh.xodim"
    compileSdk = 35

    defaultConfig {
        applicationId = "uz.sunnat.sartarosh.xodim"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0-alpha1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}
