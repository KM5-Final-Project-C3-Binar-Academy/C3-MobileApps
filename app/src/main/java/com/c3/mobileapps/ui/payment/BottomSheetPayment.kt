package com.c3.mobileapps.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetPayment(private val dataCourse: Course) : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //if data.premium > beli sekarang
        //mulai belajar
    }


}