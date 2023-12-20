package com.c3.mobileapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private val sharedPref: SharedPref by inject()

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

		val graph = navController.navInflater.inflate(R.navigation.nav_graph)

		val isLogin = sharedPref.getIsLogin()

		if (isLogin){
			graph.setStartDestination(R.id.homeFragment)
		}else{
			graph.setStartDestination(R.id.loginFragment)
		}

		navController.setGraph(graph, startDestinationArgs = null)

		binding.bottomNavigation.setupWithNavController(navController)

		navController.addOnDestinationChangedListener{_,destination,_ ->
			when(destination.id){
				R.id.searchFragment -> setBottomNav(true)
				R.id.detailCourseFragment -> setBottomNav(true)
				R.id.viewAllFragment -> setBottomNav(true)
				R.id.webViewFragment -> setBottomNav(true)
				R.id.paymentFragment -> setBottomNav(true)
				R.id.confirmPaymentFragment -> setBottomNav(true)
				R.id.viewAllKelasFragment -> setBottomNav(true)
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

	override fun onSupportNavigateUp(): Boolean {
		// Handle the back button event and return true to override
		// the default behavior the same way as the OnBackPressedCallback.
		// TODO(reason: handle custom back behavior here if desired.)

		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
		val navController = navHostFragment.navController
		val appBarConfiguration = AppBarConfiguration(navController.graph)

		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

}