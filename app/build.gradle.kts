plugins {
    id(BuildPlugins.APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.KOTLIN_PARCELIZE)
    id(BuildPlugins.DAGGER_HILT)
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
            signingConfig = signingConfigs.findByName("release")
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
}

dependencies {
    compileOnly(Libs.JAVAX_ANNOTATION)

    implementation(project(":data"))

    implementation(Libs.KOTLIN_STD_LIB)

    // android
    implementation(Libs.CORE_KTX)
    implementation(Libs.DESIGN) {
        exclude(group = "androidx.recyclerview:recyclerview", module = "recyclerview")
    }
    implementation(Libs.APPCOMPAT)
    implementation(Libs.SUPPORT_V4)
    implementation(Libs.RECYCLER_VIEW)
    implementation(Libs.CARD_VIEW) {
        exclude(group = "androidx.annotation", module = "annotation")
    }
    implementation(Libs.VECTOR_DRAWABLE)
    implementation(Libs.PREFERENCE)
    implementation(Libs.PREFERENCE_V14)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.MULTIDEX)

    implementation(Libs.LIVE_DATA)
    implementation(Libs.VIEW_MODEL)
    implementation(Libs.LIFE_CYCLE_JAVA_8)

    implementation(Libs.NAVIGATION)
    implementation(Libs.NAVIGATION_UI)

    implementation(Libs.RX_DATA_BINDINGS)
    implementation(Libs.RX_KOTLIN)

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

    testImplementation(Libs.JUNIT)
    testImplementation(Libs.MOCKITO)

    androidTestImplementation(Libs.MOCKITO)
    androidTestImplementation(Libs.ESPRESSO_CORE)
    androidTestImplementation(Libs.ESPRESSO_CONTRIB) {
        exclude(group = "androidx.appcompat", module = "appcompat")
        exclude(group = "androidx.legacy", module = "legacy-support-v4")
        exclude(group = "com.android.support", module = "design")
        exclude(module = "recyclerview")
    }
    androidTestImplementation(Libs.TEST_RUNNER)
    androidTestImplementation(Libs.TEST_RULES)
}