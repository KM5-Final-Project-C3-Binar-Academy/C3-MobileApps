package com.c3.mobileapps.ui.course

import com.c3.mobileapps.data.remote.ApiClient
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class CourseViewModelTest {
    private val instance : Retrofit = ApiClient.setRetrofit()
    private var service = ApiClient.setApiServiceCourse(instance)


    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getListCourse():Unit = runBlocking {
        val responseCourse =mockk<CourseResponse>()
        every{
            runBlocking {
                service.getCourse(null,null,null,null,null)
            }
        }returns responseCourse

        val result =  service.getCourse(null,null,null,null,null)
        runBlocking {
            service.getCourse(null,null,null,null,null)
        }
        TestCase.assertEquals(result,responseCourse)
    }
    @Test
    fun checkResponseCourseApi() {
        val instance : Retrofit = ApiClient.setRetrofit()
        val service =ApiClient.setApiServiceCourse(instance)
        val response = runBlocking {
            service.getCourse(null,null,null,null,null)
        }
        TestCase.assertNotNull(response)
    }

    @Test
    fun checkResponseCategoryCourseApi() {
        val instance : Retrofit = ApiClient.setRetrofit()
        val service =ApiClient.setApiServiceCourse(instance)
        val response = runBlocking { service.getCategory() }
        TestCase.assertNotNull(response)
    }


}