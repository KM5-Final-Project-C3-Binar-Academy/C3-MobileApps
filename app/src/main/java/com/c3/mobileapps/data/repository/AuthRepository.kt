package com.c3.mobileapps.data.repository
import com.c3.mobileapps.data.remote.model.request.auth.LoginRequest
import com.c3.mobileapps.data.remote.model.request.auth.OtpRequest
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.data.remote.model.request.auth.resetPassRequest
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.remote.service.ApiServiceAuth
import retrofit2.Response

class AuthRepository (private val apiServiceAuth: ApiServiceAuth) {
	suspend fun LoginRepo(username: String, password: String): Response <AuthResponse> {
		val loginRequest = LoginRequest(username, password)
		return apiServiceAuth.sendLogin(loginRequest)
	}

	suspend fun RegisterRepo(registerRequest: RegisterRequest): Response <AuthResponse> {
		return apiServiceAuth.sendRegister(registerRequest)
	}

	suspend fun OTPRepo(otpRequest: OtpRequest): Response <AuthResponse> {
		return apiServiceAuth.sendOTP(otpRequest)
	}

	suspend fun ResetRepo(resetPassRequest: resetPassRequest): Response <AuthResponse> {
		return apiServiceAuth.sendResetPassword(resetPassRequest)
	}
}