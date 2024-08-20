pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    val okHttpVersion = "4.9.3"
    val retrofitVersion = "2.9.0"
    val daggerVersion = "2.44"


    versionCatalogs {
        create("libs") {

            library("android.coreKtx", "androidx.core:core-ktx:1.8.0")
            library("android.appCompat", "androidx.appcompat:appcompat:1.6.1")
            library("android.constraintLayout", "androidx.constraintlayout:constraintlayout:2.1.4")
            library("android.fragmentKtx", "androidx.fragment:fragment-ktx:1.5.5")
            library("android.activityKtx", "androidx.activity:activity-ktx:1.6.1")
            library("android.lifecycleRuntimeKtx", "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
            library("android.lifecycleViewModelKtx", "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
            library("android.paging", "androidx.paging:paging-runtime-ktx:3.1.0")

            library("backend.okHttp", "com.squareup.okhttp3:okhttp:$okHttpVersion")
            library("backend.okHttpInterceptor", "com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
            library("backend.retrofit", "com.squareup.retrofit2:retrofit:$retrofitVersion")
            library("backend.moshi", "com.squareup.retrofit2:converter-moshi:$retrofitVersion")

            library("google.material", "com.google.android.material:material:1.5.0")
            library("google.hilt", "com.google.dagger:hilt-android:$daggerVersion")
            library("google.hiltCompiler", "com.google.dagger:hilt-compiler:$daggerVersion")

            library("test.junit", "junit:junit:4.13.2")
            library("test.mockk", "io.mockk:mockk:1.12.4")
            library("test.coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")

            library("jetBrains.coroutinesCore", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

            //library("modules.navigation", "navigation")
            //library("modules.core", "core")

            library("gitHub.circleImageView", "com.mikhaellopez:circularimageview:4.3.1")

            library("yandex.mapsMobile", "com.yandex.android:maps.mobile:4.1.0-full")
        }
    }
}
rootProject.name = "OrderDeliver"
include(":app")
include(":core")
include (":navigation")
