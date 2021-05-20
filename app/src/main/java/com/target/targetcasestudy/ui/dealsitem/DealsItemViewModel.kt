package com.target.targetcasestudy.ui.dealsitem

import com.target.targetcasestudy.common.ActionableLiveData
import com.target.targetcasestudy.common.BaseLiveData
import com.target.targetcasestudy.common.ObservableData
import com.target.targetcasestudy.data.repository.network.Api
import com.target.targetcasestudy.data.repository.network.Product
import com.target.targetcasestudy.data.utils.async
import com.target.targetcasestudy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DealsItemViewModel @Inject constructor(private val api: Api): BaseViewModel() {

    private val dealsItem = disposableLiveData {
        ActionableLiveData<Product>()
    }

    fun dealItem(id: Int): BaseLiveData<ObservableData<Product, Throwable>> =
        dealsItem.apply {
            actionBlock = {
                api
                    .dealsItem(id)
                    .async()
            }
        }
}