package com.c3.mobileapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		/* Kalo Mau Force Light Mode */
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
		setContentView(binding.root)

		setupNav()
	}

	private fun setupNav(){
		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
		val navController = navHostFragment.navController

		binding.bottomNavigation.setupWithNavController(navController)

		navController.addOnDestinationChangedListener{_,destination,_ ->
			when(destination.id){
				R.id.searchFragment -> setBottomNav(true)
				R.id.detailCourseFragment -> setBottomNav(true)
				R.id.viewAllFragment -> setBottomNav(true)
				R.id.webViewFragment -> setBottomNav(true)
				R.id.paymentFragment -> setBottomNav(true)
				else -> setBottomNav(false)
			}
		}
	}

	private fun setBottomNav(hide:Boolean){
		if (hide){
			binding.bottomNavigation.visibility = View.GONE
		}else{
			binding.bottomNavigation.visibility = View.VISIBLE
		}

	}

}