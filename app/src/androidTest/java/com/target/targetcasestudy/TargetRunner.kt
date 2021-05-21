package com.target.targetcasestudy

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.squareup.rx2.idler.Rx2Idler
import dagger.hilt.android.testing.HiltTestApplication
import io.reactivex.plugins.RxJavaPlugins

class TargetRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }

    override fun onStart() {
        RxJavaPlugins.setInitComputationSchedulerHandler(
            Rx2Idler.create("RxJava 2.x Computation Scheduler")
        )
        RxJavaPlugins.setInitIoSchedulerHandler(
            Rx2Idler.create("RxJava 2.x IO Scheduler")
        )
        RxJavaPlugins.setInitSingleSchedulerHandler(
            Rx2Idler.create("RxJava 2.x Single Scheduler")
        )
        RxJavaPlugins.setInitNewThreadSchedulerHandler(
            Rx2Idler.create("RxJava 2.x New Scheduler")
        )
        super.onStart()
    }
}