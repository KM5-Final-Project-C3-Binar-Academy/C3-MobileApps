package com.c3.mobileapps.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.remote.model.request.auth.OtpRequest
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.repository.AuthRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class OtpViewModel(private val authRepository: AuthRepository) : ViewModel() {
	private val _otpResponse = MutableLiveData<Response<AuthResponse>>()
	val otpResponse: LiveData<Response<AuthResponse>> get() = _otpResponse

	fun verifyOTP(otpRequest: OtpRequest) {
		viewModelScope.launch {
			_otpResponse.value = authRepository.OTPRepo(otpRequest)
		}
	}
}