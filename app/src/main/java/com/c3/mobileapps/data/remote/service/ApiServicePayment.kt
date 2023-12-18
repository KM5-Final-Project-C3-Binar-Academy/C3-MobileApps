package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.request.payment.PaymentRequest
import com.c3.mobileapps.data.remote.model.request.payment.StatusRequest
import com.c3.mobileapps.data.remote.model.response.payment.PaymentResponse
import retrofit2.Response
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
    ): PaymentResponse

    @POST("user-payments/free")
    suspend fun enrollFree(
        @Header("Authorization") token: String,
        @Body courseId: PaymentRequest
    ): PaymentResponse

    @GET("user-payments/me")
    suspend fun getAllPayment(
        @Header("Authorization") token: String,
    ): PaymentResponse

    @GET("user-payments/{id}")
    suspend fun getPaymentById(
        @Header("Authorization") token: String,
        @Path("id") id: String?
    ): PaymentResponse

    @PUT("user-payments/{id}")
    suspend fun updatePayment(
        @Header("Authorization") token: String,
        @Path("id") id: String?,
        @Body paymentMethod: StatusRequest
    ): PaymentResponse
}