package com.c3.mobileapps.ui.splashScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.SplashScreenAdapter
import com.google.android.material.tabs.TabLayout

class onBoarding : AppCompatActivity() {

    private var tabLayout : TabLayout? = null
    private var viewPager : ViewPager? = null

    private lateinit var splashScreen : ViewPager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.on_boarding)

        tabLayout = findViewById(R.id.tab_indicator)
        viewPager = findViewById(R.id.viewPagerSplash)
    }

    private fun setSplashScreenAdapter() {
        tabLayout?.setupWithViewPager(viewPager)
    }
}