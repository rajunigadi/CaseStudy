package com.target.targetcasestudy.data.repository.network

import com.google.gson.annotations.SerializedName

data class AppServerError(
    val error: Boolean?,
    val message: String?,
    val statusCode: Int?,
    val stack: String?
)

data class DealsResponse(
    val products: MutableList<Product>)

data class Product(
    val id: Int,
    val title: String,
    val aisle: String,
    val description: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("regular_price") val regularPrice: RegularPrice
)

data class RegularPrice(
    @SerializedName("amount_in_cents") val amountInCents: Int,
    @SerializedName("currency_symbol") val currencySymbol: String,
    @SerializedName("display_string") val displayString: String
)