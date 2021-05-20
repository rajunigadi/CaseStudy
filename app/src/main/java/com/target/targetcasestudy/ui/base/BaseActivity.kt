package com.target.targetcasestudy.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.target.targetcasestudy.data.repository.network.exceptions.ServerException
import com.target.targetcasestudy.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private var binding: ActivityBaseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        hideLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
    }

    private fun init() {
        if (layoutResourceId != -1) {
            binding = ActivityBaseBinding.inflate(layoutInflater)
            val view = binding?.root
            setContentView(view)
            layoutInflater.inflate(layoutResourceId, binding?.flContainer)
        }
    }

    fun displayToolbar(visible: Boolean = false) {
        if (visible) supportActionBar?.show() else supportActionBar?.hide()
    }

    fun setToolbarActionWithTitle(title: String) {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
    }

    fun showLoading() {
        binding?.layoutProgress?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding?.layoutProgress?.visibility = View.GONE
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun closeKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSnackBar(
        parentView: View,
        msg: String,
        length: Int = Snackbar.LENGTH_LONG,
        action: String? = null,
        actionCallback: () -> Unit = {}
    ) {
        parentView.let {
            val snackBar = Snackbar.make(parentView, msg, length)
            action?.let {
                snackBar.setAction(action) {
                    actionCallback.invoke()
                    snackBar.dismiss()
                }
            }
            snackBar.show()
        }
    }

    fun handleApiError(error: Throwable) {
        when (error) {
            /**
             * Check the error type and finalize the UI Call
             */
            is ServerException -> {
                if (error.serverError != null) {
                    showSnackBar(parentView = binding?.flContainer!!,
                        msg = error.serverError?.message ?: "Something went wrong"
                    )
                } else {
                    showSnackBar(parentView = binding?.flContainer!!,
                        msg = error.serverError?.message ?: "Something went wrong"
                    )
                }
            }
            else -> {
                showSnackBar(parentView = binding?.flContainer!!,
                    msg = error?.message ?: "Something went wrong"
                )
            }
        }
    }

    @get:LayoutRes
    abstract val layoutResourceId: Int
}