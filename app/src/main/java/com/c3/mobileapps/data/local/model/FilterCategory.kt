package com.c3.mobileapps.data.local.model

data class FilterCategory(
	var name: String,
	var viewName: String,
	var type: String,
	var isChecked: Boolean = false
)
