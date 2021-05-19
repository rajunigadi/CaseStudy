// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        jcenter()
        google()
        maven { url = uri(Repos.JCENTER) }
        maven { url = uri(Repos.JITPACK) }
        maven { url = uri(Repos.GOOGLE) }
    }

    dependencies {
        classpath(BuildLibs.ANDROID_BUILD)
        classpath(BuildLibs.GOOGLE_PLAY_SERVICES)
        classpath(BuildLibs.KOTLIN)
        classpath(BuildLibs.SAFE_ARGS)
        classpath(BuildLibs.DAGGER_HILT)
    }
}

allprojects {
    repositories {
        maven { url = uri(Repos.JCENTER) }
        maven { url = uri(Repos.JITPACK) }
        maven { url = uri(Repos.GOOGLE) }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}