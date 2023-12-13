package com.c3.mobileapps.data.repository
import com.c3.mobileapps.data.remote.model.authRes
import com.c3.mobileapps.data.remote.model.request.login.LoginRequest
import com.c3.mobileapps.data.remote.model.request.register.RegisterRequest
import com.c3.mobileapps.data.remote.service.ApiServiceAuth
import retrofit2.Response

class AuthRepository (private val apiServiceAuth: ApiServiceAuth) {
	suspend fun LoginRepo(username: String, password: String): Response <authRes> {
		val loginRequest = LoginRequest(username, password)
		return apiServiceAuth.sendLogin(loginRequest)
	}

	suspend fun RegisterRepo(registerRequest: RegisterRequest): Response <authRes> {
		return apiServiceAuth.sendRegister(registerRequest)
	}
}