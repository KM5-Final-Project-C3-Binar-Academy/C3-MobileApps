package com.c3.mobileapps.ui.profile

import com.c3.mobileapps.data.remote.ApiClient
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test
import retrofit2.Retrofit

class ProfileViewModelTest {
    private val instance : Retrofit = ApiClient.setRetrofit()
    private var service = ApiClient.setApiServiceUser(instance)


    @Before
    fun setUp() {
        service = mockk()
    }


    @Test
    fun getUserResp():Unit = runBlocking {
        val responseCourse = mockk<AuthResponse>()
        every{
            runBlocking {
                service.getUser("aaaa")
            }
        } returns responseCourse

        val result =  service.getUser("aaaa")
        runBlocking {
            service.getUser("aaaa")
        }
        TestCase.assertEquals(result,responseCourse)
    }





}