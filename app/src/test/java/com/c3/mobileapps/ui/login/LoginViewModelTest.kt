package com.c3.mobileapps.ui.login

import com.c3.mobileapps.data.remote.ApiClient
import com.c3.mobileapps.data.remote.model.request.auth.LoginRequest
import com.c3.mobileapps.data.remote.model.request.auth.resetPassRequest
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit

class LoginViewModelTest{

    private val instance : Retrofit = ApiClient.setRetrofit()
    private var service = ApiClient.setApiServiceAuth(instance)


    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun loginAuth():Unit = runBlocking {
        val responseCourse =mockk<Response<AuthResponse>>()
        val loginRequest = LoginRequest("username", "password")
        every{
            runBlocking {
                service.sendLogin(loginRequest)
            }
        } returns responseCourse

        val result = service.sendLogin(loginRequest)
        runBlocking {
            service.sendLogin(loginRequest)
        }
        TestCase.assertEquals(result,responseCourse)
    }

    @Test
    fun checkResponseApiServiceAuthLogin() {
        val instance : Retrofit = ApiClient.setRetrofit()
        val service =ApiClient.setApiServiceAuth(instance)
        val loginRequest = LoginRequest("rem@rezero.com", "rem-rezero")
        val response = runBlocking {
            service.sendLogin(loginRequest)
        }
        TestCase.assertNotNull(response)
    }

    @Test
    fun checkResponseResetPassword() {
        val instance : Retrofit = ApiClient.setRetrofit()
        val service =ApiClient.setApiServiceAuth(instance)
        val modelData = resetPassRequest("regapwg@gmail.com")
        val response = runBlocking {
            service.sendResetPassword(modelData)
        }
        TestCase.assertNotNull(response)
    }
}