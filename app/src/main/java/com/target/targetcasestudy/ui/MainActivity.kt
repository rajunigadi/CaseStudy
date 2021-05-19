package com.target.targetcasestudy.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.target.targetcasestudy.R
import com.target.targetcasestudy.databinding.ActivityMainBinding
import com.target.targetcasestudy.ui.base.BaseActivity
import com.target.targetcasestudy.ui.deals.DealListFragment
import com.target.targetcasestudy.ui.payment.PaymentDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var binding: ActivityMainBinding? = null

    override val layoutResourceId: Int get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DealListFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.credit_card -> {
                PaymentDialogFragment().show(supportFragmentManager, "CreditCardValidation")
                true
            }
            else -> false
        }
    }
}
