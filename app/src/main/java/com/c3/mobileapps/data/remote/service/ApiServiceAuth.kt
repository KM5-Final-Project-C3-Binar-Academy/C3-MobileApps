package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.request.auth.LoginRequest
import com.c3.mobileapps.data.remote.model.request.auth.OtpRequest
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.data.remote.model.request.auth.resetPassRequest
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceAuth {
	@POST("auth/login")
	suspend fun sendLogin(
		@Body loginRequest: LoginRequest
	): Response<AuthResponse>

	@POST("auth/register")
	suspend fun sendRegister(
		@Body registerRequest: RegisterRequest
	): Response<AuthResponse>

	@POST("auth/otp/verify")
	suspend fun sendOTP(
		@Body otpRequest: OtpRequest
	): Response<AuthResponse>

	@POST("/auth/password-reset")
	suspend fun sendResetPassword(
		@Body resetPassRequest: resetPassRequest
	): Response<AuthResponse>


}