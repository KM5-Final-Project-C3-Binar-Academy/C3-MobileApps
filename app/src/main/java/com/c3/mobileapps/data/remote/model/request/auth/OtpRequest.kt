package com.c3.mobileapps.data.remote.model.request.auth

import android.os.Parcel
import android.os.Parcelable

data class OtpRequest(
	val email: String,
	val otp: String
)