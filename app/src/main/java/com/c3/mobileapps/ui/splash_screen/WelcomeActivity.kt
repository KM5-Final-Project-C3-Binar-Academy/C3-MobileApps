package com.c3.mobileapps.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.WelcomeSlideAdapter
import com.c3.mobileapps.data.local.model.IntroSlide
import com.c3.mobileapps.databinding.ActivityWelcomeBinding
import com.c3.mobileapps.ui.main_activity.MainActivity


class WelcomeActivity : AppCompatActivity() {

    private val welcomeSlideAdapter = WelcomeSlideAdapter(
        listOf(
            IntroSlide(
                R.drawable.icon_learnify,
                "LEARNIFY",
                "dari Pengalaman Terbaik!",
                R.drawable.welcome1
            ),
            IntroSlide(
                R.drawable.icon_learnify,
                "LEARNIFY",
                "dari Praktisi Terbaik!",
                R.drawable.welcome2
            ),
            IntroSlide(
                R.drawable.icon_learnify,
                "LEARNIFY",
                "darimana saja!",
                R.drawable.welcome3
            )
        )
    )
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.welcomePager.adapter = welcomeSlideAdapter
        setUpIndicator()
        setCurrentIndicator(0)
        binding.welcomePager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        val pager = binding.welcomePager
        binding.btnNext.setOnClickListener {
            if (pager.currentItem + 1 < welcomeSlideAdapter.itemCount) {
                pager.currentItem += 1
            } else {
                Intent(applicationContext, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }

        binding.btnlogin.setOnClickListener {
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

    }

    private fun setUpIndicator() {
        val indicators = arrayOfNulls<ImageView>(welcomeSlideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicat_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.indicator.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicator.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicator[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicat_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicat_inactive
                    )
                )
            }
        }

    }
}