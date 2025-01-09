plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android") // Hilt plugin
}

android {
    namespace = "com.example.MilkySip"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.MilkySip"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.room.common)
    implementation(libs.room.runtime.android)
    annotationProcessor(libs.room.compiler)

    // https://mvnrepository.com/artifact/androidx.room/room-runtime
    runtimeOnly(libs.room.runtime)
    // https://mvnrepository.com/artifact/org.apache.maven.plugin-tools/maven-plugin-annotations
    compileOnly(libs.maven.plugin.annotations)

    //Dependency injection
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}