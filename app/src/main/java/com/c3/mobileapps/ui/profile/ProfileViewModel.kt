package com.c3.mobileapps.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.repository.UserRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository, private val sharedPref: SharedPref): ViewModel(){
    private val token: String =  sharedPref.getToken()

    private var _isLogin = MutableLiveData<Boolean>().apply { value = sharedPref.getIsLogin() }
    val isLogin: LiveData<Boolean> get() = _isLogin

    private var _userResp: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val userResp: LiveData<Resource<AuthResponse>> get() = _userResp
    fun getCurrentUser() = viewModelScope.launch {
        getUser()
    }

    private suspend fun getUser() {
        try {
            val responses = userRepository.getUser(token)
            _userResp.value = Resource.success(responses)

        } catch (exception: Exception) {
            _userResp.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }
}