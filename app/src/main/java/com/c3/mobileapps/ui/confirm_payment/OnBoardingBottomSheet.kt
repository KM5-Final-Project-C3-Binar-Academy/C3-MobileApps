package com.c3.mobileapps.ui.confirm_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c3.mobileapps.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class OnBoardingBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottomsheet_on_boarding, container, false)
    }


}