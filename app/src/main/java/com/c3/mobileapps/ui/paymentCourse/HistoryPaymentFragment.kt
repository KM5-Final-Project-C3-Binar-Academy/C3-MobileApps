package com.c3.mobileapps.ui.paymentCourse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c3.mobileapps.databinding.FragmentHistoryPaymentBinding


class HistoryPaymentFragment : Fragment() {
    private lateinit var binding: FragmentHistoryPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = FragmentHistoryPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonUpBack()
    }

    private fun buttonUpBack() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


}