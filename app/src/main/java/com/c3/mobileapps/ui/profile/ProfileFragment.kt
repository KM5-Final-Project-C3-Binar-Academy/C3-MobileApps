package com.c3.mobileapps.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.databinding.FragmentProfileBinding
import com.c3.mobileapps.ui.nonlogin.NonLoginBottomSheet
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by inject()


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
            findNavController().navigate(R.id.editProfileFragment)
        }

        binding.tvChangePassword.setOnClickListener {

        }

        binding.tvHistoryPayment.setOnClickListener {
            findNavController().navigate(R.id.historyPaymentFragment)

        }

        binding.tvLogout.setOnClickListener {

        }

        checkIsLogin()


    }
    private fun checkIsLogin() {
        profileViewModel.isLogin.observe(viewLifecycleOwner){isLogin ->
            if (isLogin){
                getCurrentUser()
            }else{
                val nonLoginBottomSheet = NonLoginBottomSheet()
                nonLoginBottomSheet.isCancelable = false
                nonLoginBottomSheet.show(childFragmentManager, nonLoginBottomSheet.tag)
            }
        }
    }

    private fun getCurrentUser(){
        profileViewModel.getCurrentUser()
        profileViewModel.userResp.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Category", Gson().toJson(it.data))
                    val data = it.data?.data

                    binding.tvNama.text = data?.name
                }

                Status.ERROR -> {
                    Log.e("Cek Data Category", it.message.toString())
                }

                Status.LOADING -> {

                }
            }
        }
    }
}