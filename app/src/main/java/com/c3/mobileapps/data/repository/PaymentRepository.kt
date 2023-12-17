package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.remote.model.request.payment.PaymentRequest
import com.c3.mobileapps.data.remote.service.ApiServicePayment

class PaymentRepository(private val apiServicePayment: ApiServicePayment) {
    suspend fun makePayment(token:String, courseId:String) = apiServicePayment.makePayment(token,
        PaymentRequest(courseId)
    )
    suspend fun getPayment(token:String) = apiServicePayment.getPayment(token)
}