package com.c3.mobileapps.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentBottomsheetOnBoardingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class OnBoardingBottomSheet(private val dataCourse: Course, private val currentFragment:Int) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomsheetOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBottomsheetOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onboarding.text = dataCourse.onboardingText

        binding.btnMulaiBelajar.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(currentFragment, true)
                .build()

            findNavController().navigate(R.id.kelasFragment, args = null, navOptions = navOptions)
        }

    }


}