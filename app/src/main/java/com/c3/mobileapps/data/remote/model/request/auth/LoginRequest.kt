package com.c3.mobileapps.data.remote.model.request.auth
data class LoginRequest(
	var email: String?,
	var phone_number: String?,
	var password: String?
)
