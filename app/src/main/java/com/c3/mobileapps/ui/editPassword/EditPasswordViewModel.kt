package com.c3.mobileapps.ui.editPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.request.user.EditPassword
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class EditPasswordViewModel(private val userRepo: UserRepository, private val sharedPref: SharedPref) : ViewModel() {
	private val token: String =  sharedPref.getToken()
	private val _edtPassResp = MutableLiveData<Response<AuthResponse>> ()
	val edtPassResp: LiveData<Response<AuthResponse>> get() = _edtPassResp

	fun editPass(editPassword: EditPassword){
		viewModelScope.launch {
			_edtPassResp.value = userRepo.editPass(token,editPassword)
		}
	}

}