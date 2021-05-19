package com.target.targetcasestudy.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T: RecyclerView.ViewHolder>: RecyclerView.Adapter<T>() {

    var attachedToRecyclerView = false

    fun <T : ViewDataBinding> ViewGroup.getViewBinding(@LayoutRes res: Int): T {
        return DataBindingUtil.inflate(LayoutInflater.from(this.context), res, this, false)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        attachedToRecyclerView = true
    }
}