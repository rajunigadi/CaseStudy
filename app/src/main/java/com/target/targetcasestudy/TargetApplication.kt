package com.target.targetcasestudy

import android.app.Application
import com.target.targetcasestudy.data.utils.timber.DebugTree
import com.target.targetcasestudy.data.utils.timber.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TargetApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(if(BuildConfig.DEBUG) DebugTree() else ReleaseTree())
    }
}