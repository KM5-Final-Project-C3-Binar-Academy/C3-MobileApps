package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.request.payment.PaymentRequest
import com.c3.mobileapps.data.remote.model.response.payment.PaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServicePayment {

    @POST("user-payments")
    suspend fun makePayment(
        @Header("Authorization") token: String,
        @Body courseId: PaymentRequest
    ): PaymentResponse

    @GET("user-payments/me")
    suspend fun getPayment(
        @Header("Authorization") token: String,
    ): PaymentResponse
}