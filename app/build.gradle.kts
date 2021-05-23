plugins {
    id(BuildPlugins.APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.KOTLIN_PARCELIZE)
    id(BuildPlugins.DAGGER_HILT)
    id(BuildPlugins.SAFE_ARGS)
}

configurations.all {
    resolutionStrategy {
        force(Libs.OKHTTP)
        force(Libs.OBJENESIS) // Failed to process: /Users/rajashekhar/.gradle/caches/transforms-2/files-2.1/6860a39e2930813f8da314c0345bd911/jetified-objenesis-3.0.1.jar
    }
}

android {

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    compileSdkVersion(AndroidSdk.COMPILE)

    defaultConfig {
        minSdkVersion(AndroidSdk.MIN)
        targetSdkVersion(AndroidSdk.TARGET)

        applicationId = BuildPlugins.APPLICATION_ID
        versionCode = Versions.APP_VERSION_CODE
        versionName = Versions.APP_VERSION_NAME
        testInstrumentationRunner = BuildPlugins.TEST_INSTRUMENTATION_RUNNER
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        setTestHandleProfiling(true)
        setTestFunctionalTest(true)

        resConfigs("en")
    }

    buildTypes {

        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
            proguardFiles("proguard-library.pro")
            isTestCoverageEnabled = false
        }
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    sourceSets {
        val sharedTestDir = "src/sharedTest/java"
        val sharedTestResDir = "src/sharedTest/resources"
        getByName("test") {
            java.srcDirs(sharedTestDir)
            resources.srcDirs(sharedTestResDir)
        }
        getByName("androidTest") {
            java.srcDirs(sharedTestDir)
            resources.srcDirs(sharedTestResDir)
        }
    }
}

dependencies {
    implementation(project(":data"))

    implementation(Libs.KOTLIN_STD_LIB)

    // android
    implementation(Libs.CORE_KTX)
    implementation(Libs.DESIGN) {
        exclude(group = "androidx.recyclerview:recyclerview", module = "recyclerview")
    }
    implementation(Libs.APPCOMPAT)
    implementation(Libs.RECYCLER_VIEW)
    implementation(Libs.VECTOR_DRAWABLE)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.MULTIDEX)

    implementation(Libs.LIVE_DATA)
    implementation(Libs.VIEW_MODEL)
    implementation(Libs.LIFE_CYCLE_JAVA_8)

    implementation(Libs.NAVIGATION)
    implementation(Libs.NAVIGATION_UI)

    implementation(Libs.RX_ANDROID) {
        exclude(group = "io.reactivex", module = "rxjava")
    }

    implementation(Libs.RETROFIT)
    implementation(Libs.OKHTTP_LOG)

    //dagger
    implementation(Libs.DAGGER_HILT)
    kapt(Libs.DAGGER_HILT_COMPILER)

    implementation(Libs.GSON_CONVERTER)

    implementation(Libs.GLIDE)
    kapt(Libs.GLIDE_COMPILER)

    implementation(Libs.TIMBER)

    // debug for testing
    debugImplementation(Libs.FRAGMENT_TESTING)

    // local testing
    testImplementation(Libs.JUNIT)

    testImplementation(Libs.TEST_DAGGER_HILT)
    kaptTest(Libs.TEST_DAGGER_HILT_COMPILER)
    testImplementation(Libs.MOCK_ANDROID)
    testImplementation(Libs.MOCK_WEB_SERVER)


    // instrumentation testing
    androidTestImplementation(Libs.TEST_EXT_JUNIT)
    androidTestImplementation(Libs.TEST_EXT_TRUTH)
    androidTestImplementation(Libs.TEST_GOOGLE_TRUTH)

    androidTestImplementation(Libs.TEST_RUNNER)
    androidTestImplementation(Libs.TEST_RULES)

    androidTestImplementation(Libs.ESPRESSO_CORE)
    androidTestImplementation(Libs.ESPRESSO_CONTRIB)

    androidTestImplementation(Libs.TEST_DAGGER_HILT)
    kaptAndroidTest(Libs.TEST_DAGGER_HILT_COMPILER)
    androidTestImplementation(Libs.MOCK_ANDROID)
    androidTestImplementation(Libs.MOCK_WEB_SERVER)

    androidTestImplementation(Libs.NAVIGATION_TESTING)

    androidTestImplementation(Libs.RX_IDLER)
    androidTestImplementation(Libs.OKHTTP_IDLING_RESOURCE)
}