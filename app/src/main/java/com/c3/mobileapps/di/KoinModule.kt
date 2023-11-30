package com.c3.mobileapps.di

import com.c3.mobileapps.data.remote.ApiClient
import com.c3.mobileapps.data.repository.CourseRepository
import com.c3.mobileapps.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {

    val apiModule
        get() = module {
            single { ApiClient.setRetrofit() }
            single {ApiClient.setApiServiceCourse(get()) }
        }

    val remoteModule
        get() = module {
            single { CourseRepository(get()) }
        }

    val viewModelModule
        get() = module {
            viewModel { HomeViewModel(get()) }
        }
}