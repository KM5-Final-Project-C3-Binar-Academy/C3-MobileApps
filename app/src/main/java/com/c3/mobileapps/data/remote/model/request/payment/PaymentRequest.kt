package com.c3.mobileapps.data.remote.model.request.payment

import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("course_id")
    val courseId: String
)
