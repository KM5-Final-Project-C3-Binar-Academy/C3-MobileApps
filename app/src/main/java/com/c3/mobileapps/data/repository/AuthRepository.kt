package com.c3.mobileapps.data.repository
<<<<<<< Updated upstream
import com.c3.mobileapps.data.remote.model.request.auth.LoginRequest
import com.c3.mobileapps.data.remote.model.request.auth.OtpRequest
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
=======

import com.c3.mobileapps.data.remote.model.authRes
import com.c3.mobileapps.data.remote.model.request.login.LoginRequest
import com.c3.mobileapps.data.remote.model.request.register.RegisterRequest
>>>>>>> Stashed changes
import com.c3.mobileapps.data.remote.service.ApiServiceAuth
import retrofit2.Response

class AuthRepository (private val apiServiceAuth: ApiServiceAuth) {
<<<<<<< Updated upstream
	suspend fun LoginRepo(username: String, password: String): Response <AuthResponse> {
=======
	suspend fun LoginRepo(username: String, password: String): Response <authRes> {
>>>>>>> Stashed changes
		val loginRequest = LoginRequest(username, password)
		return apiServiceAuth.sendLogin(loginRequest)
	}

<<<<<<< Updated upstream
	suspend fun RegisterRepo(registerRequest: RegisterRequest): Response <AuthResponse> {
		return apiServiceAuth.sendRegister(registerRequest)
	}

	suspend fun OTPRepo(otpRequest: OtpRequest): Response <AuthResponse> {
		return apiServiceAuth.sendOTP(otpRequest)
	}
=======
	suspend fun RegisterRepo(registerRequest: RegisterRequest): Response <authRes> {
		return apiServiceAuth.sendRegister(registerRequest)
	}

>>>>>>> Stashed changes
}