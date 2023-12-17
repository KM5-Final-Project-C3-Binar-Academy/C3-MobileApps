package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.remote.model.request.payment.PaymentRequest
import com.c3.mobileapps.data.remote.model.request.payment.StatusRequest
import com.c3.mobileapps.data.remote.service.ApiServicePayment

class PaymentRepository(private val apiServicePayment: ApiServicePayment) {
    suspend fun makePayment(token:String, courseId:String) = apiServicePayment.makePayment(token,
        PaymentRequest(courseId)
    )
    suspend fun getAllPayment(token:String) = apiServicePayment.getAllPayment(token)

    suspend fun getPayment(token: String, id:String) = apiServicePayment.getPaymentById(token,id)

    suspend fun updatePayment(token: String, id:String, status:StatusRequest) = apiServicePayment.updatePayment(token,id,status)
}