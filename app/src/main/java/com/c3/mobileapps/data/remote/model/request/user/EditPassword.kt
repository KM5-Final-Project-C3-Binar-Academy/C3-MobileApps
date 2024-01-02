package com.c3.mobileapps.data.remote.model.request.user

data class EditPassword(
	val old_password: String,
	val new_password: String
)