package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.request.payment.PaymentRequest
import com.c3.mobileapps.data.remote.model.request.payment.StatusRequest
import com.c3.mobileapps.data.remote.model.response.payment.PaymentIdResponse
import com.c3.mobileapps.data.remote.model.response.payment.PaymentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServicePayment {

    @POST("user-payments")
    suspend fun makePayment(
        @Header("Authorization") token: String,
        @Body courseId: PaymentRequest
    ): PaymentIdResponse

    @POST("user-payments/free")
    suspend fun enrollFree(
        @Header("Authorization") token: String,
        @Body courseId: PaymentRequest
    ): PaymentIdResponse

    @GET("user-payments/me")
    suspend fun getAllPayment(
        @Header("Authorization") token: String,
    ): PaymentResponse

    @GET("user-payments/{id}")
    suspend fun getPaymentById(
        @Header("Authorization") token: String,
        @Path("id") id: String?
    ): PaymentIdResponse

    @PUT("user-payments/{id}")
    suspend fun updatePayment(
        @Header("Authorization") token: String,
        @Path("id") id: String?,
        @Body paymentMethod: StatusRequest
    ): PaymentIdResponse
}