package com.c3.mobileapps.data.remote.model.request.register

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String,
    val phone_number: String
)