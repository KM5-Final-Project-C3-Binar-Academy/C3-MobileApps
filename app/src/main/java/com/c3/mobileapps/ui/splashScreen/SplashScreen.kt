package com.c3.mobileapps.ui.splashScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.SplashScreenAdapter
import com.c3.mobileapps.databinding.SplashScreenBinding
import com.google.android.material.tabs.TabLayout

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    var splashScreenAdapter : SplashScreenAdapter? = null
    var tabLayout : TabLayout? = null
    var splashScreen : ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        setContentView(R.layout.splash_screen)

        tabLayout = findViewById(R.id.tab_indicator)

        //data
        val splashScreenData:MutableList<SplashScreenData> = ArrayList()
        splashScreenData.add(SplashScreenData("Learnify", "Dari pengalaman terbaik!!", R.drawable.project_planning))
        splashScreenData.add(SplashScreenData("Learnify", "Dari praktisi terbaik!!", R.drawable.analytics))
        splashScreenData.add(SplashScreenData("Learnify", "Dari mana saja", R.drawable.share_location))

        setSplashScreenAdapter(splashScreenData)

    }

    private fun setSplashScreenAdapter(SplashScreenData: List<SplashScreenData>){

        splashScreenAdapter = SplashScreenAdapter(this, SplashScreenData)
        splashScreen = findViewById(R.id.viewPagerSplash)
        splashScreen!!.adapter = splashScreenAdapter
        tabLayout?.setupWithViewPager(splashScreen)

    }
}