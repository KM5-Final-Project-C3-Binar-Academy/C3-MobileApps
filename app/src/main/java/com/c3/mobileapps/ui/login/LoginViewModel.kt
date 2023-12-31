package com.c3.mobileapps.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.remote.model.request.auth.resetPassRequest
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
	private val _loginResponse = MutableLiveData<Response<AuthResponse>>()
	val loginResponse: LiveData<Response<AuthResponse>> get() = _loginResponse

	fun login(username: String?, phoneNumber:String?, password: String,) {
		viewModelScope.launch {
			_loginResponse.value = authRepository.LoginRepo(username, phoneNumber, password)
		}
	}

	fun resetPassword(email: String){
		viewModelScope.launch {
			val modelData = resetPassRequest(email)
			_loginResponse.value = authRepository.ResetRepo(modelData)
		}
	}
}