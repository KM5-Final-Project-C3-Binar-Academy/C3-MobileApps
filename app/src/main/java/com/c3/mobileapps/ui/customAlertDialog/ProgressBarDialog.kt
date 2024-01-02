package com.c3.mobileapps.ui.customAlertDialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.c3.mobileapps.R

class ProgressBarDialog(context: Context) : AlertDialog(context){
	init {
		setCancelable(false)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.alert_progressbar_layout)
	}
}