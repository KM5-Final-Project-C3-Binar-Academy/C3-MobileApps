package com.c3.mobileapps.ui.confirm_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c3.mobileapps.R
import com.c3.mobileapps.databinding.FragmentBottomSheetPaymentBinding
import com.c3.mobileapps.databinding.FragmentPaymentSuccessBottomSheetBinding

class paymentSuccessBottomSheet : Fragment() {

    private lateinit var binding : FragmentPaymentSuccessBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding= FragmentPaymentSuccessBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

}