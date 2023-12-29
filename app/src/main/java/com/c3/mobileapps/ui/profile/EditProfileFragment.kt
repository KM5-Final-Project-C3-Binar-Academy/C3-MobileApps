package com.c3.mobileapps.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.c3.mobileapps.R
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.data.remote.model.request.user.EditUser
import com.c3.mobileapps.data.remote.model.response.user.User
import com.c3.mobileapps.databinding.FragmentEditProfileBinding
import com.c3.mobileapps.utils.CustomSnackbar
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import javax.inject.Inject


class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by inject()
    private val sharedPreferences: SharedPref by inject()
    private val snackbar = CustomSnackbar()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing Current User Data
        getCurrentUser()

        binding.btnSimpan.setOnClickListener {
            // Get Data from Form Edit Profile
            val name    = binding.inputUsername.text.toString()
            val email   = binding.inputEmail.text.toString()
            val telp    = binding.inputTlp.text.toString()

            // Verified Data

            // Wrap to Dataclass
            val modelData = EditUser(name,email,telp)

            // Send Data to API
            editProfileViewModel.setUpdateUser(modelData)

            // Get Response from API
            editProfileViewModel.userResp.observe(viewLifecycleOwner){
                val res = it.message.toString()

                // Display to Snackbar or Log
                when (it.status) {
                    Status.SUCCESS -> {
                        snackbar.showSnackbarUtils(it.message,false, layoutInflater, requireView(), requireContext())
                        Log.i("Edit Profile Issues", res)
                    }

                    Status.ERROR -> {
                        snackbar.showSnackbarUtils(res,true, layoutInflater, requireView(), requireContext())
                        Log.i("Edit Profile Issues", res)
                    }

                    Status.LOADING -> {

                    }
                }
            }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getCurrentUser(){
        editProfileViewModel.getCurrentUser()
        editProfileViewModel.userResp.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Edit Profile Issues", Gson().toJson(it.data))
                    val data = it.data?.data

                    data?.image?.let { imageUrl ->
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .into(binding.imgProfile)
                    }

                    binding.inputUsername.setText(data?.name)
                    binding.inputEmail.setText(data?.email)
                    binding.inputTlp.setText(data?.phoneNumber)
                }

                Status.ERROR -> {
                    Log.e("Edit Profile Issues", it.message.toString())
                }

                Status.LOADING -> {

                }
            }
        }
    }
}