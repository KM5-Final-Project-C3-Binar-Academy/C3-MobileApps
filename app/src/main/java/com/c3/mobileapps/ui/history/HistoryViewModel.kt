package com.c3.mobileapps.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.response.payment.PaymentResponse
import com.c3.mobileapps.data.repository.PaymentRepository
import com.c3.mobileapps.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel(private val paymentRepository: PaymentRepository, private val sharedPref: SharedPref): ViewModel() {

    private val token: String =  sharedPref.getToken()

    private var _isLogin = MutableLiveData<Boolean>().apply { value = sharedPref.getIsLogin() }
    val isLogin: LiveData<Boolean> get() = _isLogin

    private val _paymentResp = MutableLiveData<Resource<PaymentResponse>>()
    val paymentResp: LiveData<Resource<PaymentResponse>> get() = _paymentResp

    fun getListPayment() = viewModelScope.launch {
        getAllPayment()
    }

    private suspend fun getAllPayment() {
        try {

            val responses = paymentRepository.getAllPayment(token)
            _paymentResp.value = Resource.success(responses)

        } catch (exception: Exception) {
            _paymentResp.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }
}