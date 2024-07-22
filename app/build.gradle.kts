import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.bananasplit"
    compileSdk = 33

    packaging {
        resources {
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
    defaultConfig {
        applicationId = "com.example.bananasplit"
        minSdk = 30
        targetSdk = 33
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
    // Enable View Binding
    viewBinding {
        enable = true
    }
    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.camera.view)
    implementation(libs.room.common)
    implementation(libs.room.runtime)
    implementation(libs.camera.core)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.gson)
    implementation(libs.core.splashscreen)
    implementation(libs.imagepicker)
//    implementation(libs.navigation.fragment)
//    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.junit)
    annotationProcessor(libs.room.compiler)
    implementation(libs.paypal.card.payments)
    implementation(libs.android.paypal.native.payments)
    implementation(libs.checkout.android.sdk)
    implementation(libs.security.crypto)
    implementation(libs.payment.buttons)
    implementation (libs.google.cloud.language)
    implementation(libs.hilt.android)
    implementation (libs.google.auth.library.oauth2.http)
    implementation (libs.grpc.okhttp)
    implementation (libs.grpc.protobuf)
    implementation (libs.grpc.stub)
    implementation (libs.google.cloud.vision)


    //fix for kotlin duplicate calss error which erose from nowhere?!?!
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }


}