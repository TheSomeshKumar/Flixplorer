import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.thesomeshkumar.flixplorer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thesomeshkumar.flixplorer"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val key: String = gradleLocalProperties(rootDir, providers).getProperty("TMDB_KEY")
        forEach {
            it.buildConfigField("String", "TMDB_KEY", "\"$key\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE-notice.md,LICENSE.md}"
        }
    }

    testOptions {
        packaging {
            jniLibs {
                useLegacyPackaging = true
            }
        }
        unitTests.apply {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
        animationsDisabled = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.compose.navigation)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.datastore)

    implementation(libs.compose.material3)
    implementation(libs.compose.material.icon)
    implementation(libs.compose.ui)
    implementation(libs.compose.constraint.layout)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.util)

    implementation(libs.paging)
    implementation(libs.paging.compose)

    implementation(libs.hilt)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)

    coreLibraryDesugaring(libs.desuger)

    implementation(libs.coroutine)
    implementation(libs.coroutine.android)

    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.convertor)

    implementation(libs.coil)
    implementation(libs.coil.video)
    implementation(libs.lottie.compose)

    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.turbin)
    testImplementation(libs.strikt)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.chucker.library.no.np)
}
