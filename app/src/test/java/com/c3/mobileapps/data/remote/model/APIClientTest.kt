package com.c3.mobileapps.data.remote.model

import com.c3.mobileapps.data.remote.ApiClient
import com.c3.mobileapps.data.remote.model.request.auth.LoginRequest
import com.c3.mobileapps.data.remote.model.request.auth.OtpRequest
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit

class APIClientTest {
    @Test
    fun getInstance() {
        val baseUrl = "https://api.belajar.risalamin.com/"
        val instance : Retrofit = ApiClient.setRetrofit()
        assert(instance.baseUrl().toUrl().toString() == baseUrl)
    }

    @Test
    fun checkResponseCategoryCourseApi() {
        val instance : Retrofit = ApiClient.setRetrofit()
        val service =ApiClient.setApiServiceCourse(instance)
        val response = runBlocking { service.getCategory() }
        TestCase.assertNotNull(response)
    }

    @Test
    fun checkResponseApiServiceAuthLogin() {
        val instance : Retrofit = ApiClient.setRetrofit()
        val service =ApiClient.setApiServiceAuth(instance)
        val loginRequest = LoginRequest("rem@rezero.com", "rem-rezero")
        val response = runBlocking { service.sendLogin(loginRequest) }
        TestCase.assertNotNull(response)
    }

    @Test
    fun checkResponseApiServiceAuth() {
        val instance : Retrofit = ApiClient.setRetrofit()
        val service =ApiClient.setApiServiceAuth(instance)
        val modelData = OtpRequest("regapwg@gmail.com", "00000")
        val response = runBlocking { service.sendOTP(modelData) }
        TestCase.assertNotNull(response)
    }
}