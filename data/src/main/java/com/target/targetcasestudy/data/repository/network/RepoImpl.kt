package com.target.targetcasestudy.data.repository.network

import io.reactivex.Single

class RepoImpl(private val api: Api) : Repo {
    override fun deals(): Single<DealsResponse> = api.deals()
    override fun dealsItem(id: Int): Single<Product> = api.dealsItem(id)
}
