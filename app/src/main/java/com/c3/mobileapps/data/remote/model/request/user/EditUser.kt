package com.c3.mobileapps.data.remote.model.request.user

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class EditUser(
	val name: String,
	val email: String,
	val phone_number: String,
	val image: File
)