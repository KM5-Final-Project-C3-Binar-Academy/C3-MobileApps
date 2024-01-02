package com.c3.mobileapps.data.remote.model.request.payment

import com.google.gson.annotations.SerializedName

data class StatusRequest(
    @SerializedName("payment_method")
    val paymentMethod: String
)
