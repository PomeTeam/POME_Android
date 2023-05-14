plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.teampome.pome"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"http://13.125.237.136:8085/\"")
            buildConfigField("String", "IMAGE_BASE_URL", "\"http://image-main-server.ap-northeast-2.elasticbeanstalk.com/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"http://52.79.89.129/\"")
            buildConfigField("String", "IMAGE_BASE_URL", "\"http://image-main-server.ap-northeast-2.elasticbeanstalk.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        enable = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Fragment ViewModel
    val fragmentKtxVersion = "1.5.3"
    implementation("androidx.fragment:fragment-ktx:$fragmentKtxVersion")

    // Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.google.code.gson:gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    val okhttp_version = "4.10.0"
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp_version")

    // Glide
    val glide_version = "4.13.2"
    implementation("com.github.bumptech.glide:glide:$glide_version")
    kapt("com.github.bumptech.glide:compiler:$glide_version")
    implementation("com.github.bumptech.glide:okhttp3-integration:$glide_version"){
        exclude(group = "glide-parent")
    }
    implementation("com.github.bumptech.glide:annotations:$glide_version")
    annotationProcessor("com.github.bumptech.glide:compiler:$glide_version")
    implementation("com.caverock:androidsvg-aar:1.4")

    // Glide Tranform
    implementation("jp.wasabeef:glide-transformations:4.3.0")

    // Navigation
    val navVersion = "2.5.2"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Hilt
    val hiltVersion = "2.44"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    val kotlinxMetadataJvmVersion = "0.5.0"
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:$kotlinxMetadataJvmVersion")

    // Calendar
    implementation("com.github.prolificinteractive:material-calendarview:2.0.1")
    implementation("com.jakewharton.threetenabp:threetenabp:1.1.1")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Paging
    val pagingVersion = "3.1.1"
    implementation("androidx.paging:paging-runtime:$pagingVersion")
}