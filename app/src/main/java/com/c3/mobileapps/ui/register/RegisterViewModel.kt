package com.c3.mobileapps.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val authRepository: AuthRepository): ViewModel() {
	private val _registerResponse = MutableLiveData<Response<AuthResponse>>()
	val registerResponse: LiveData<Response<AuthResponse>> get() = _registerResponse

	fun sendData(registerRequest: RegisterRequest) {
		viewModelScope.launch {
			_registerResponse.value = authRepository.RegisterRepo(registerRequest)
		}
	}
}