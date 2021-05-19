package com.target.targetcasestudy.data.utils.timber

import android.os.Build
import android.util.Log
import timber.log.Timber

class DebugTree : Timber.DebugTree() {

    override fun log(prior: Int, tag: String?, message: String, t: Throwable?) {
        var priority = prior
        // Workaround for devices that doesn't show lower priority logs
        if (Build.MANUFACTURER == "HUAWEI" || Build.MANUFACTURER == "samsung") {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                priority = Log.ERROR
            }
        }
        super.log(priority, tag, message, t)
    }

    override fun createStackElementTag(element: StackTraceElement): String {
        // Add log statements line number to the log
        return super.createStackElementTag(element) + " - " + element.lineNumber
    }
}