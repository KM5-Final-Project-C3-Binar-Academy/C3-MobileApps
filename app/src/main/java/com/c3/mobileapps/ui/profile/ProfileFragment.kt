package com.c3.mobileapps.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProfil.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.tvChangePassword.setOnClickListener {

        }

        binding.tvHistoryPayment.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_historyPaymentFragment)

        }

        binding.tvLogout.setOnClickListener {

        }
    }
}