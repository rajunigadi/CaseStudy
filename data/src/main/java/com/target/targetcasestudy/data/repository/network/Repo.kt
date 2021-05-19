package com.target.targetcasestudy.data.repository.network

import io.reactivex.Single

interface Repo {
    fun deals(): Single<DealsResponse>

    fun dealsItem(id: Int): Single<MutableList<Product>>
}