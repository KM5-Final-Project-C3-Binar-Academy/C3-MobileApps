package com.c3.mobileapps.ui.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.request.user.EditUser
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.remote.model.response.user.User
import com.c3.mobileapps.data.repository.UserRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch
import java.io.File

class EditProfileViewModel(private val userRepo: UserRepository, private val sharedPref: SharedPref) : ViewModel() {
	private val token: String =  sharedPref.getToken()

	private var _userResp: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
	val userResp: LiveData<Resource<AuthResponse>> get() = _userResp

	fun updateUser(user: EditUser, image: File) = viewModelScope.launch {
		setUser(user, image)
	}

	fun updateUserWithoutImage(user: EditUser) = viewModelScope.launch {
		setUserWithoutImage(user)
	}

	private suspend fun setUser(user: EditUser, image:File){
		try {
			val responses = userRepo.updateUser(token,user,image)
			_userResp.value = Resource.success(responses)

		} catch (exception: Exception) {
			_userResp.value = Resource.error( null,  exception.message ?: "Error Occurred!")
		}
	}

	private suspend fun setUserWithoutImage(user: EditUser){
		try {
			val responses = userRepo.updateUserWithoutImage(token,user)
			_userResp.value = Resource.success(responses)

		} catch (exception: Exception) {
			_userResp.value = Resource.error( null,  exception.message ?: "Error Occurred!")
		}
	}
}