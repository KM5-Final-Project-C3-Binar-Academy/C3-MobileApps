package com.c3.mobileapps.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentPaymentSuccessBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentSuccessBottomSheet(private val course: Course) : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentPaymentSuccessBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding= FragmentPaymentSuccessBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMulaiBelajar.setOnClickListener {
            val onBoardingBottomSheet = OnBoardingBottomSheet(course)
            onBoardingBottomSheet.isCancelable = false
            onBoardingBottomSheet.show(childFragmentManager, onBoardingBottomSheet.tag)

        }

        binding.btnKembaliKeberanda.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

}