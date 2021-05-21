package com.target.targetcasestudy

import android.app.Application
import com.target.targetcasestudy.data.utils.timber.DebugTree
import com.target.targetcasestudy.data.utils.timber.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

@HiltAndroidApp
class TargetApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(if(BuildConfig.DEBUG) DebugTree() else ReleaseTree())

        //GlobalErrorFilter errorFilter = new GlobalErrorFilter(BuildConfig.DEBUG /* alwaysCrash */);
        RxJavaPlugins.setErrorHandler { throwable ->
            /*if (errorFilter.filter(throwable)) {
                // Crash in some cases
                throw new RuntimeException(throwable);
            } else {
                // Just log it in others
                Timber.e(throwable, "Ignoring uncaught Rx exception: %s", throwable.getLocalizedMessage());
            }*/
            Timber.e("RxJava2 global error handler: " + throwable.message)
            //FirebaseCrashlytics.getInstance().recordException(throwable);
        }
    }
}