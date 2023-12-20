package com.c3.mobileapps.ui.nonlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.databinding.NonLoginBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NonLoginBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: NonLoginBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NonLoginBottomSheetBinding.inflate(inflater,container, false)

//        val screenHeight = resources.displayMetrics.heightPixels
//        val desiredHeight = (screenHeight * 0.73).toInt()
//
//        val layoutParams = binding.viewBottomSheet.layoutParams
//        layoutParams.height = desiredHeight
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.profileFragment, true)
                .build()

            dismiss()
            findNavController().navigate(R.id.homeFragment, args = null, navOptions = navOptions)
        }

        binding.btnToLogin.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()

            dismiss()
            findNavController().navigate(R.id.loginFragment, args = null, navOptions = navOptions)
        }
    }
}