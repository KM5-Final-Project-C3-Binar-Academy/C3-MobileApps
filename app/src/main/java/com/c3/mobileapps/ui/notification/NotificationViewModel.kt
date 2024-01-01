package com.c3.mobileapps.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.response.notification.NotificationIdResponse
import com.c3.mobileapps.data.remote.model.response.notification.NotificationResponse
import com.c3.mobileapps.data.repository.NotificationRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.launch

class NotificationViewModel(private val notificationRepository: NotificationRepository, private val sharedPref: SharedPref): ViewModel() {

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

            val responses = notificationRepository.readAllNotif(token)
            _notifResp.postValue(Resource.success(responses))

        } catch (exception: Exception) {
            _notifResp.postValue(Resource.error( null,  exception.message ?: "Error Occurred!"))
        }
    }

    fun readAllNotif() = viewModelScope.launch {
        updateAllNotif()
    }

    private suspend fun updateAllNotif() {
        try {

            val responses = notificationRepository.readAllNotif(token)
            _notifResp.postValue(Resource.success(responses))

        } catch (exception: Exception) {
            _notifResp.postValue(Resource.error( null,  exception.message ?: "Error Occurred!"))
        }
    }

    private val _notifByIdResp = MutableLiveData<Resource<NotificationIdResponse>>()
    val notifIdResp: LiveData<Resource<NotificationIdResponse>> get() = _notifByIdResp

    fun readNotif(id:String) = viewModelScope.launch {
        updateNotif(id)
    }

    private suspend fun updateNotif(idNotif: String) {
        try {

            val responses = notificationRepository.updateNotif(token, idNotif)
            _notifByIdResp.postValue(Resource.success(responses))

        } catch (exception: Exception) {
            _notifByIdResp.postValue(Resource.error( null,  exception.message ?: "Error Occurred!"))
        }
    }

    fun deleteNotif(id:String) = viewModelScope.launch {
        deletedNotif(id)
    }

    private suspend fun deletedNotif(idNotif: String) {
        try {

            val responses = notificationRepository.deleteNotif(token, idNotif)
            _notifByIdResp.postValue(Resource.success(responses))

        } catch (exception: Exception) {
            _notifByIdResp.postValue(Resource.error( null,  exception.message ?: "Error Occurred!"))
        }
    }
}