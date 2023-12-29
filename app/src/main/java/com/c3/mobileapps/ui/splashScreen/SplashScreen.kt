package com.c3.mobileapps.ui.splashScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.viewpager.widget.ViewPager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.SplashScreenAdapter
import com.c3.mobileapps.databinding.FragmentLoginBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.w3c.dom.Text

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var splashScreenAdapter : SplashScreenAdapter
    private lateinit var splashScreen : ViewPager
    private lateinit var tabLayout: TabLayout
    var next : TextView? = null
    var position = 0
    private lateinit var tvLoginSplash : TextView


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_splash_screen)

        tabLayout = findViewById(R.id.tabLayoutSplash)
        next = findViewById(R.id.btnNextSplash)


        //data
        val splashScreenData:MutableList<SplashScreenData> = ArrayList()
        splashScreenData.add(SplashScreenData("Learnify", "Dari pengalaman terbaik!!", R.drawable.project_planning))
        splashScreenData.add(SplashScreenData("Learnify", "Dari praktisi terbaik!!", R.drawable.analytics))
        splashScreenData.add(SplashScreenData("Learnify", "Dari mana saja", R.drawable.share_location))

        setSplashScreenAdapter(splashScreenData)

        next?.setOnClickListener{
            if (position < splashScreenData.size){
                position++
                splashScreen!!.currentItem = position
            }
        }

        position = splashScreen.currentItem


        tvLoginSplash.findNavController().navigate(R.id.loginFragment)

        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                position = tab!!.position
                    if(tab.position == splashScreenData.size -1){
                        next!!.text = "Get Started"
                }else{
                    next!!.text = "Next"
                    }
            }
        })

    }

    private fun setSplashScreenAdapter(SplashScreenData: List<SplashScreenData>){

        splashScreenAdapter = SplashScreenAdapter(this, SplashScreenData)
        splashScreen = findViewById(R.id.viewPagerSplash)
        splashScreen.adapter = splashScreenAdapter


    }


}
