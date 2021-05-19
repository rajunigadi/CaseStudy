object Repos {
    const val JCENTER                       = "https://jcenter.bintray.com/"
    const val JITPACK                       = "https://jitpack.io/"
    const val GOOGLE                        = "https://dl.google.com/dl/android/maven2/"
}

object BuildPlugins {
    const val APPLICATION                   = "com.android.application"
    const val ANDROID_LIBRARY               = "com.android.library"
    const val JAVA_LIBRARY                  = "java-library"
    const val KOTLIN                        = "kotlin"
    const val KOTLIN_JVM                    = "org.jetbrains.kotlin.jvm"
    const val GOOGLE_PLAY_SERVICES          = "com.google.gms.google-services"
    const val KOTLIN_ANDROID                = "kotlin-android"
    const val KOTLIN_PARCELIZE              = "kotlin-parcelize"
    const val KOTLIN_KAPT                   = "kotlin-kapt"
    const val SAFE_ARGS                     = "androidx.navigation.safeargs.kotlin"
    const val DAGGER_HILT                   = "dagger.hilt.android.plugin"

    const val APPLICATION_ID                = "com.target.targetcasestudy"
    const val TEST_APPLICATION_ID           = "com.target.targetcasestudy.lithium.test"

    const val TEST_INSTRUMENTATION_RUNNER   = "com.target.targetcasestudy.lithium.app.util.MockTestRunner"
}

object AndroidSdk {
    const val MIN                           = 21
    const val TARGET                        = 30
    const val COMPILE                       = 30
}

object Versions {
    //private val versions                    = GitVersions.getVersion() // can be recieved versions from git branch
    const val APP_VERSION_CODE                    = 1 //versions[0].toString().toInt()
    const val APP_VERSION_NAME                    = "1.0" //versions[1].toString()
}