package com.c3.mobileapps.ui.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.c3.mobileapps.databinding.ActivitySplashBinding
import com.c3.mobileapps.ui.main_activity.MainActivity
import com.c3.mobileapps.utils.Status
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        /* Kalo Mau Force Light Mode */
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        getStatusUser()

    }

    private fun getStatusUser(){
        splashViewModel.getCurrentUser()
        splashViewModel.userResp.observe(this){
            when (it.status) {
                Status.SUCCESS -> {
                    binding.pbLoading.visibility = View.INVISIBLE
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                Status.ERROR -> {
                    //masuk ke onboarding
                    binding.pbLoading.visibility = View.INVISIBLE
                    splashViewModel.setIsLogin(false)
                    splashViewModel.setToken("")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                Status.LOADING -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
            }
        }

    }
}