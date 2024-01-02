package com.c3.mobileapps.data.remote.model.request.user

import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class EditUser(
	val name: String,
	val email: String,
	val phone_number: String
)