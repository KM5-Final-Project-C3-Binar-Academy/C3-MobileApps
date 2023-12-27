package com.c3.mobileapps.ui.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.c3.mobileapps.R
import com.c3.mobileapps.ui.home.HomeFragment


@SuppressLint("CustomSplashScreen")
class SplashScreen1Activity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.splash_screen1)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@SplashScreen1Activity, SplashScreen::class.java)
            startActivity(intent)
            finish()
        },2000)


    }
}