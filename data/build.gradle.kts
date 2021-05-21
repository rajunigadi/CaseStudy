plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.KOTLIN_PARCELIZE)
}

configurations.all {
    resolutionStrategy {
        force(Libs.OKHTTP)
    }
}

android {
    compileSdkVersion(AndroidSdk.TARGET)

    defaultConfig {
        minSdkVersion(AndroidSdk.MIN)
        targetSdkVersion(AndroidSdk.TARGET)

        resConfigs("en")
        consumerProguardFiles("proguard-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        exclude("LICENSE.txt")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(Libs.KOTLIN_STD_LIB)

    implementation(Libs.RX_ANDROID) {
        exclude(group = "io.reactivex", module = "rxjava")
    }
    implementation(Libs.RETROFIT)
    implementation(Libs.GSON_CONVERTER)
    implementation(Libs.RETROFIT_RXJAVA_ADAPTER) {
        exclude(group = "io.reactivex", module = "rxjava")
        exclude(group = "com.squareup.retrofit2", module = "retrofit")
    }

    implementation(Libs.TIMBER)

    testImplementation(Libs.JUNIT)
}