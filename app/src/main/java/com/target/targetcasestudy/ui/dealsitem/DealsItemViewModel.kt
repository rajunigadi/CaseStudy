package com.target.targetcasestudy.ui.dealsitem

import com.target.targetcasestudy.common.ActionableLiveData
import com.target.targetcasestudy.common.BaseLiveData
import com.target.targetcasestudy.common.ObservableData
import com.target.targetcasestudy.data.repository.network.Product
import com.target.targetcasestudy.data.repository.network.Repo
import com.target.targetcasestudy.data.utils.async
import com.target.targetcasestudy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DealsItemViewModel @Inject constructor(private val repo: Repo): BaseViewModel() {

    private val dealsItem = disposableLiveData {
        ActionableLiveData<Product>()
    }

    fun dealItem(id: Int): BaseLiveData<ObservableData<Product, Throwable>> =
        dealsItem.apply {
            actionBlock = {
                repo
                    .dealsItem(id)
                    .async()
            }
        }
}