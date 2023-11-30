package com.c3.mobileapps.di

import com.c3.mobileapps.data.database.courseDB.CourseDatabase
import com.c3.mobileapps.data.remote.ApiClient
import com.c3.mobileapps.data.repository.CourseRepository
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.data.repository.RoomRepository
import com.c3.mobileapps.ui.course.CourseViewModel
import com.c3.mobileapps.ui.detailCourse.DetailCourseViewModel
import com.c3.mobileapps.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {

    val dataModule
        get() = module{
            single { CourseDatabase.getInstance(context = get())}
            factory { get<CourseDatabase>().courseDao }
        }

    val apiModule
        get() = module {
            single { ApiClient.setRetrofit() }
            single {ApiClient.setApiServiceCourse(get()) }
        }

    val remoteModule
        get() = module {
            factory { CourseRepository(get()) }
            factory { DataRepository(get(), get()) }
            factory { RoomRepository(get())}
        }

    val viewModelModule
        get() = module {
            viewModel { HomeViewModel(get()) }
            viewModel { CourseViewModel(get())}
            viewModel { DetailCourseViewModel()}
        }
}