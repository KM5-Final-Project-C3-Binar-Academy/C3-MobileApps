package com.c3.mobileapps.ui.confirm_payment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.request.payment.StatusRequest
import com.c3.mobileapps.data.remote.model.response.payment.PaymentIdResponse
import com.c3.mobileapps.data.remote.model.response.payment.PaymentResponse
import com.c3.mobileapps.data.repository.PaymentRepository
import com.c3.mobileapps.utils.Resource

class CfrmPaymentViewModel(private val paymentRepository: PaymentRepository, private val sharedPref: SharedPref):ViewModel() {

    private val token: String =  sharedPref.getToken()

    private var _isLogin = MutableLiveData<Boolean>().apply { value = sharedPref.getIsLogin() }
    val isLogin: LiveData<Boolean> get() = _isLogin

    private val _paymentResp = MutableLiveData<Resource<PaymentIdResponse>>()
    val paymentResp: LiveData<Resource<PaymentIdResponse>> get() = _paymentResp

    suspend fun updateStatus(courseId: String, method: StatusRequest) {
        try {
            Log.e("Payment",token)

            val responses = paymentRepository.updatePayment(token, courseId, method)
            _paymentResp.value = Resource.success(responses)

        } catch (exception: Exception) {
            _paymentResp.value = Resource.error( null,  exception.message ?: "Error Occurred!")
        }
    }

}