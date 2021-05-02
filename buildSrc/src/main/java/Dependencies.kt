import org.gradle.api.JavaVersion

object Config {
    const val compile_sdk = 30
    const val min_sdk = 23
    const val target_sdk = 30
    const val application_id = "com.androidapp.appcleanarch"
    val java_version = JavaVersion.VERSION_1_8
    const val build_tools_version = "30.0.3"
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Version {
    //Room
    const val roomRuntime = "2.2.6"
    const val roomCompiler = "2.2.6"
    const val roomKtx = "2.2.6"

    //Koin
    const val koinAndroid = "2.0.1"
    const val koinAndroidViewModel = "2.0.1"

    //Coroutine
    const val coroutinesCore = "1.4.1"
    const val coroutinesAndroid = "1.4.1"

    //Dagger
    const val daggerAndroid = "2.17"
    const val daggerСompiler = "2.17"

    //ViewModel
    const val lifecycleViewmodelKtx = "2.3.1"
    const val lifecycleExtensions = "2.2.0"

    //Glide
    const val glide = "4.11.0"

    // Retrofit 2
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val loggingInterceptor = "4.9.0"
    const val retrofit2Rxjava2Adapter = "1.0.0"
    const val retrofit2KotlinCoroutinesAdapter = "0.9.2"

    // Rx-Java
    const val rxAndroid = "2.1.1"
    const val rxJava = "2.2.8"

    //Test
    const val jUnit = "4.13.2"
    const val runner = "1.1.2"
    const val espressoCore = "3.3.0"

    //Design
    const val appcompat = "1.2.0"
    const val material = "1.2.0-alpha02"
    const val constraintLayout = "2.0.4"

    //Kotlin
    const val core = "1.3.2"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Version.roomRuntime}"
    const val compiler = "androidx.room:room-compiler:${Version.roomCompiler}"
    const val room_ktx = "androidx.room:room-ktx:${Version.roomKtx}"
}

object Koin {
    const val koin_android = "org.koin:koin-android:${Version.koinAndroid}"
    const val koin_android_viewmodel = "org.koin:koin-android-viewmodel:${Version.koinAndroidViewModel}"
}

object Coroutine {
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutinesCore}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutinesAndroid}"
}

object Dagger {
    const val dagger_android = "com.google.dagger:dagger-android:${Version.daggerAndroid}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Version.daggerСompiler}"
}

object ViewModel {
    const val lifecycle_viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleViewmodelKtx}"
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Version.lifecycleExtensions}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Version.converterGson}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Version.loggingInterceptor}"
    const val retrofit2_rxjava2_adapter = "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Version.retrofit2Rxjava2Adapter}"
    const val retrofit2_kotlin_coroutines_adapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Version.retrofit2KotlinCoroutinesAdapter}"
}

object Rxjava {
    const val rx_android = "io.reactivex.rxjava2:rxandroid:${Version.rxAndroid}"
    const val rx_java = "io.reactivex.rxjava2:rxjava:${Version.rxJava}"
}

object TestImpl {
    const val junit = "junit:junit:${Version.jUnit}"
    const val runner = "androidx.test:runner:${Version.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.espressoCore}"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val material = "com.google.android.material:material:${Version.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Version.core}"
}