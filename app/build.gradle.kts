import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.orderdeliver"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.orderdeliver"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            apiVersion = "1.8"
            languageVersion = "1.8"
        }
    }


    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.android.coreKtx)
    implementation(libs.android.appCompat)
    implementation(libs.android.constraintLayout)
    implementation(libs.android.fragmentKtx)
    implementation(libs.android.activityKtx)
    implementation(libs.android.lifecycleRuntimeKtx)
    implementation(libs.android.lifecycleViewModelKtx)
    implementation(libs.android.paging)

    implementation(libs.google.material)
    implementation(libs.google.hilt)
    kapt(libs.google.hiltCompiler)

    implementation(libs.jetBrains.coroutinesCore)

    implementation(libs.backend.okHttp)
    implementation(libs.backend.okHttpInterceptor)
    implementation(libs.backend.moshi)
    implementation(libs.backend.retrofit)

    implementation(libs.gitHub.circleImageView)

    implementation(libs.yandex.mapsMobile)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.mockk)

    implementation(project(":navigation"))
    implementation(project(":core"))

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}