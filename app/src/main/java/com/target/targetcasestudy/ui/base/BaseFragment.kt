package com.target.targetcasestudy.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
abstract class BaseFragment: Fragment() {

    val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeKeyboard()
        configureView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposables.isDisposed) {
            disposables.dispose()
            disposables.clear()
        }
        closeKeyboard()
    }

    open fun configureView() = Unit

    fun setToolbarActionWithTitle(title: String) {
        (activity as BaseActivity)?.supportActionBar?.let { actionBar ->
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = title
        }
    }

    fun setToolbarActionWithTitle(toolbar: Toolbar, title: String) {
        (activity as BaseActivity)?.setSupportActionBar(toolbar)
        (activity as BaseActivity)?.supportActionBar?.let { actionBar ->
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = title
        }
    }

    fun setToolbarAction(toolbar: Toolbar) {
        (activity as BaseActivity)?.setSupportActionBar(toolbar)
        (activity as BaseActivity)?.supportActionBar?.let { actionBar ->
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = ""
        }
    }

    fun closeKeyboard() {
        (activity as BaseActivity).closeKeyboard()
    }

    fun showToast(msg: String) {
        (activity as? BaseActivity)?.showToast(msg)
    }

    fun showSnackBar(
        parentView: View?,
        msg: String,
        length: Int = Snackbar.LENGTH_LONG,
        action: String? = null,
        actionCallback: () -> Unit = {}
    ) {
        if(parentView != null) {
            (activity as? BaseActivity)?.showSnackBar(
                parentView, msg, length, action, actionCallback
            )
        } else {
            showToast(msg)
        }
    }

    fun handleApiError(error: Throwable?) {
        error?.let { it ->
            (activity as? BaseActivity)?.handleApiError(it)
        }
    }
}