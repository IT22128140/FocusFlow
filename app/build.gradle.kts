plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.focusflow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.focusflow"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation (libs.androidx.room.runtime)//room
    //noinspection KaptUsageInsteadOfKsp
    kapt (libs.androidx.room.compiler)//room
    implementation (libs.androidx.room.ktx)//room

    implementation(libs.androidx.core.ktx)//core
    implementation(libs.androidx.appcompat)//appcompat
    implementation(libs.material)//material
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)//constraintlayout
    implementation(libs.androidx.navigation.fragment.ktx)//navigation
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)//junit
    androidTestImplementation(libs.androidx.junit)//junit
    androidTestImplementation(libs.androidx.espresso.core)//espresso
    implementation (libs.androidx.lifecycle.livedata.ktx)//livedata
    implementation (libs.androidx.lifecycle.viewmodel.ktx)//viewmodel
    implementation (libs.androidx.activity.ktx)//activity


}