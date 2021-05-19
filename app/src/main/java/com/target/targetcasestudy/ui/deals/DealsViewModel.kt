package com.target.targetcasestudy.ui.deals

import com.target.targetcasestudy.common.ActionableLiveData
import com.target.targetcasestudy.common.BaseLiveData
import com.target.targetcasestudy.common.ObservableData
import com.target.targetcasestudy.data.repository.network.Api
import com.target.targetcasestudy.data.repository.network.DealsResponse
import com.target.targetcasestudy.data.utils.async
import com.target.targetcasestudy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DealsViewModel @Inject constructor(private val api: Api): BaseViewModel() {

    private val deals = disposableLiveData {
        ActionableLiveData<DealsResponse>()
    }

    fun deals(): BaseLiveData<ObservableData<DealsResponse, Throwable>> =
        deals.apply {
            actionBlock = {
                api
                    .deals()
                    .async()
            }
        }
}