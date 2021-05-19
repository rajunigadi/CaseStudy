package com.target.targetcasestudy.data.repository.network

import io.reactivex.Single
import timber.log.Timber

class RepoImpl(private val api: Api) : Repo {

    init {
        Timber.d("RepoImpl created!")
    }

    override fun deals(): Single<DealsResponse> = api.deals()

    override fun dealsItem(id: Int): Single<MutableList<Product>> = api.dealsItem(id)
}
