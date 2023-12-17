package com.c3.mobileapps.data.remote.model.response.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("data")
    var `data`: Payment?,
    @SerializedName("message")
    var message: String?
)