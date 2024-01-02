package com.c3.mobileapps.ui.profile

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.c3.mobileapps.R
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.databinding.FragmentProfileBinding
import com.c3.mobileapps.ui.nonlogin.NonLoginBottomSheet
import com.c3.mobileapps.databinding.ItemCustomSnackbarBinding
import com.c3.mobileapps.utils.CustomSnackbar
import com.c3.mobileapps.utils.Status
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.koin.android.ext.android.inject


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by inject()
    private val sharedPreferences: SharedPref by inject()
    private val snackbar = CustomSnackbar()

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
            findNavController().navigate(R.id.editPasswordFragment)
        }

        binding.tvHistoryPayment.setOnClickListener {
            findNavController().navigate(R.id.historyPaymentFragment)
        }

        binding.tvLogout.setOnClickListener {
            alertDialog()
        }

        checkIsLogin()


    }
    private fun checkIsLogin() {
        profileViewModel.isLogin.observe(viewLifecycleOwner){isLogin ->
            if (isLogin){
                getCurrentUser()
            }else{
                val nonLoginBottomSheet = NonLoginBottomSheet(R.id.profileFragment)
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

                    data?.image?.let { imageUrl ->
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .into(binding.imgProfile)
                    }
                }

                Status.ERROR -> {
                    Log.e("Cek Data Category", it.message.toString())
                }

                Status.LOADING -> {

                }
            }
        }
    }

    private fun alertDialog(){
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Konfirmasi")
            .setMessage("Apakah kamu yakin untuk keluar akun?")
            .setPositiveButton("YA") { dialog, which ->
                sharedPreferences.clearPreferences()
                findNavController().navigate(R.id.loginFragment)
                snackbar.showSnackbarUtils("Berhasil Logout!", false, layoutInflater, requireView(), requireContext())
            }
            .setNegativeButton("TIDAK") { dialog, which ->
                dialog.dismiss()
            }

        // Create and show the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}