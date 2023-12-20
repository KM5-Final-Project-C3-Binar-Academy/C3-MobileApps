package com.c3.mobileapps.ui.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.response.notification.NotificationResponse
import com.c3.mobileapps.data.remote.model.response.payment.PaymentResponse
import com.c3.mobileapps.data.repository.NotificationRepository
import com.c3.mobileapps.data.repository.PaymentRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val notificationRepository: NotificationRepository, private val sharedPref: SharedPref) : ViewModel() {

    private val token: String =  sharedPref.getToken()

    private var _isLogin = MutableLiveData<Boolean>().apply { value = sharedPref.getIsLogin() }
    val isLogin: LiveData<Boolean> get() = _isLogin

    private val _notifResp = MutableLiveData<Resource<NotificationResponse>>()
    val notifResp: LiveData<Resource<NotificationResponse>> get() = _notifResp

    fun getListNotif() = viewModelScope.launch {
        getAllNotif()
    }

    private suspend fun getAllNotif() {
        try {

            val responses = notificationRepository.getNotification(token)
            _notifResp.value = Resource.success(responses)

        } catch (exception: Exception) {
            _notifResp.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }
}