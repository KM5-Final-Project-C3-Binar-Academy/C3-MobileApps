package com.c3.mobileapps.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import com.c3.mobileapps.R

class SplashScreen1Activity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen1)

        progressBar = findViewById(R.id.loadingSplash)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@SplashScreen1Activity, onBoarding::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
