package com.c3.mobileapps.data.remote.model

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit

class APIClientTest {
    @Test
    fun getInstance() {
        val baseUrl = "https://api-binar-backend.risalamin.com/"
        val instance : Retrofit = APIClient.instance
        assert(instance.baseUrl().toUrl().toString() == baseUrl)
    }

    @Test
    fun checkResponseCategoryCourseApi() {
        val service = APIClient.endpointAPIService
        val response = runBlocking { service.getCategory() }
        TestCase.assertNotNull(response)
    }

    @Test
    fun checkResponseListCourseApi() {
        val service = APIClient.endpointAPIService
        val response = runBlocking { service.getCourses() }
        TestCase.assertNotNull(response)
    }
}