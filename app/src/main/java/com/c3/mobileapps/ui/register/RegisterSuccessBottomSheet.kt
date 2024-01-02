package com.c3.mobileapps.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.R
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.databinding.BottomsheetRegisterSuccessBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RegisterSuccessBottomSheet : BottomSheetDialogFragment() {
	private lateinit var binding: BottomsheetRegisterSuccessBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = BottomsheetRegisterSuccessBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.btnHomepage.setOnClickListener{
//			findNavController().navigate(.id.homeFragment)
		}
	}
}