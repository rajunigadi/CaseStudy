package com.target.targetcasestudy.ui.base

import androidx.lifecycle.ViewModel
import com.target.targetcasestudy.common.BaseLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {

    private val disposables = CompositeDisposable()
    private val allLiveData: MutableSet<BaseLiveData<*>> = mutableSetOf()

    fun addDisposable(disposableBlock: () -> Disposable) {
        disposables.add(disposableBlock.invoke())
    }

    fun <T : BaseLiveData<*>> disposableLiveData(block: () -> T): T {
        return block.invoke().apply {
            allLiveData.add(this)
        }
    }

    override fun onCleared() {
        disposables.dispose()
        allLiveData.forEach {
            it.clearAllDisposables()
        }
        allLiveData.clear()
    }
}