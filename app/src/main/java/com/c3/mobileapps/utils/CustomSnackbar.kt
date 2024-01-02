package com.c3.mobileapps.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.c3.mobileapps.R
import com.c3.mobileapps.databinding.ItemCustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar

// Under Developing! Don't Use This Class!
class CustomSnackbar{
	 fun showSnackbarUtils(message: String?, error: Boolean, layoutInflater: LayoutInflater, view: View, context: Context) {
		val customSnackbarBinding = ItemCustomSnackbarBinding.inflate(layoutInflater)
		val customSnackbarView = customSnackbarBinding.root

		// Set teks pada Snackbar kustom
		customSnackbarBinding.tvMessage.text = message.toString()

		// Create a Snackbar with the root view of the fragment
		val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
		val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

		// Set warna latar belakang Snackbar
		if (error) {
			customSnackbarBinding.layoutSnackbar.background = ColorDrawable(Color.parseColor("#F31559"))
		} else {
			customSnackbarBinding.ivLogo.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.check_circle_24))
			customSnackbarBinding.layoutSnackbar.background = ColorDrawable(Color.parseColor("#8ADAB2"))
		}
		snackbarLayout.setPadding(0,0,0,0)

		// Add the custom view to SnackbarLayout
		snackbarLayout.addView(customSnackbarView, 0)
		snackbar.show()
	}
}