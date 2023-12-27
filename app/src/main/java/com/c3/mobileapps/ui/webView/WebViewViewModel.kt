package com.c3.mobileapps.ui.webView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.response.updateCourseMaterial.DataMaterialStatus
import com.c3.mobileapps.data.remote.model.response.updateCourseMaterial.MaterialStatusResponse
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import kotlin.time.Duration.Companion.minutes

class WebViewViewModel(private val repository: DataRepository, private val sharedPref: SharedPref): ViewModel() {

    private val token: String =  sharedPref.getToken()

    private var _isLogin = MutableLiveData<Boolean>().apply { value = sharedPref.getIsLogin() }
    val isLogin: LiveData<Boolean> get() = _isLogin


    private val _materialResp = MutableLiveData<Resource<MaterialStatusResponse>>()
    val materialResp: LiveData<Resource<MaterialStatusResponse>> get() = _materialResp
    fun getUpdateMaterial(id: String?= null) = viewModelScope.launch {
       delay(1.minutes)
        updateMaterial(id)
    }

    private suspend fun updateMaterial(id: String? = null) {
        try {

            val responses = repository.remote.updateCourseMaterial(token, id)
            _materialResp.value = Resource.success(responses)

        } catch (exception: Exception) {
            _materialResp.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }

}