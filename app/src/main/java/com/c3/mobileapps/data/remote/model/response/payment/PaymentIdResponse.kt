package com.c3.mobileapps.data.remote.model.response.payment


import com.google.gson.annotations.SerializedName

data class PaymentIdResponse(
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var `data`: Payment
)