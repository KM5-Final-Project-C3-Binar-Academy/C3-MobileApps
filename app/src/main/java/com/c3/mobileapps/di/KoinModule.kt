package com.c3.mobileapps.di

import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.local.database.categoryDB.CategoryDatabase
import com.c3.mobileapps.data.local.database.categoryDB2.CategoryDB
import com.c3.mobileapps.data.remote.ApiClient
import com.c3.mobileapps.data.repository.AuthRepository
import com.c3.mobileapps.data.repository.CourseRepository
import com.c3.mobileapps.data.repository.DataRepository
import com.c3.mobileapps.data.repository.NotificationRepository
import com.c3.mobileapps.data.repository.PaymentRepository
import com.c3.mobileapps.data.repository.RoomRepository
import com.c3.mobileapps.data.repository.UserRepository
import com.c3.mobileapps.ui.confirm_payment.CfrmPaymentViewModel
import com.c3.mobileapps.ui.course.CourseViewModel
import com.c3.mobileapps.ui.detailCourse.DetailCourseViewModel
import com.c3.mobileapps.ui.history.HistoryViewModel
import com.c3.mobileapps.ui.home.HomeViewModel
import com.c3.mobileapps.ui.search.SearchViewModel
import com.c3.mobileapps.ui.kelas.KelasViewModel
import com.c3.mobileapps.ui.login.LoginViewModel
import com.c3.mobileapps.ui.main_activity.MainViewModel
import com.c3.mobileapps.ui.notification.NotificationViewModel
import com.c3.mobileapps.ui.payment.PaymentViewModel
import com.c3.mobileapps.ui.profile.ProfileViewModel
import com.c3.mobileapps.ui.register.RegisterViewModel
import com.c3.mobileapps.ui.splash_screen.SplashViewModel
import com.c3.mobileapps.ui.webView.WebViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {

    val dataModule
        get() = module{
            /*Database offline first category course*/
            single { CategoryDatabase.getInstance(context = get())}
            factory { get<CategoryDatabase>().categoryDao }

            single { CategoryDB.getInstance(get()) }
            factory { get<CategoryDB>().categorydao }
        }

    val apiModule
        get() = module {
            single { ApiClient.setRetrofit() }
            single { ApiClient.setApiServiceCourse(get()) }
            single { ApiClient.setApiServiceAuth(get())}
            single { ApiClient.setApiServicePayment(get()) }
            single { ApiClient.setApiServiceUser(get()) }
            single { ApiClient.setApiServiceNotification(get()) }
        }

    val remoteModule
        get() = module {
            factory { CourseRepository(get()) }
            factory { DataRepository(get(), get()) }
            factory { RoomRepository(get()) }
            factory { AuthRepository(get())}
            factory { PaymentRepository(get()) }
            factory { UserRepository(get()) }
            factory { NotificationRepository(get()) }
        }

    val sharedPreferences
        get() = module {
            factory { SharedPref(get()) }
        }

    val viewModelModule
        get() = module {
            viewModel { HomeViewModel(get(), get()) }
            viewModel { CourseViewModel(get())}
            viewModel { LoginViewModel(get())}
            viewModel { RegisterViewModel(get())}
            viewModel { DetailCourseViewModel(get(), get())}
            viewModel { SearchViewModel(get()) }
            viewModel { KelasViewModel(get(),get(), get()) }
            viewModel { HistoryViewModel(get(),get()) }
            viewModel { PaymentViewModel(get(),get())}
            viewModel { CfrmPaymentViewModel(get(),get()) }
            viewModel { ProfileViewModel(get(),get()) }
            viewModel { SplashViewModel(get(),get()) }
            viewModel { MainViewModel(get(),get()) }
            viewModel { NotificationViewModel(get(),get()) }
            viewModel { WebViewViewModel(get(),get()) }
        }
}