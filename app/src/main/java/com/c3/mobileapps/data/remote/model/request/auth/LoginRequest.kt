package com.c3.mobileapps.data.remote.model.request.auth

import com.google.gson.annotations.SerializedName

data class LoginRequest(
	@SerializedName("email")
	val email : String?,
	@SerializedName("phone_number")
	val phoneNumber: String?,
	@SerializedName("password")
	val password : String?
)
