package com.target.targetcasestudy.data.repository.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

const val DEALS = "deals"
const val DEALS_ITEM = "deals/{id}"

interface Api {

    @GET(DEALS)
    fun deals(): Single<DealsResponse>

    @GET(DEALS_ITEM)
    fun dealsItem(@Path("id") id: Int): Single<Product>
}