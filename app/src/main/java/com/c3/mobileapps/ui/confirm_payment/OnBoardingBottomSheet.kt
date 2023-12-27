package com.c3.mobileapps.ui.confirm_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentBottomsheetOnBoardingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class OnBoardingBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomsheetOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBottomsheetOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }


}