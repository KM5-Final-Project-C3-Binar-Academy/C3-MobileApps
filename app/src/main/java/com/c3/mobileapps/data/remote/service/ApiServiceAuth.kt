package com.c3.mobileapps.data.remote.service

import com.c3.mobileapps.data.remote.model.authRes
import com.c3.mobileapps.data.remote.model.request.login.LoginRequest
import com.c3.mobileapps.data.remote.model.request.register.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceAuth {
	@POST("auth/login")
	suspend fun sendLogin(
		@Body loginRequest: LoginRequest
	): Response<authRes>

	@POST("auth/register")
	suspend fun sendRegister(
		@Body registerRequest: RegisterRequest
	): Response<authRes>
}