package com.target.targetcasestudy.common

import androidx.fragment.app.Fragment
import com.target.targetcasestudy.ui.base.BaseActivity

fun Fragment.showLoading() {
    when (activity) {
        is BaseActivity -> (activity as BaseActivity).showLoading()
    }
}

fun Fragment.hideLoading() {
    when (activity) {
        is BaseActivity -> (activity as BaseActivity).hideLoading()
    }
}