package com.c3.mobileapps.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.request.user.EditUser
import com.c3.mobileapps.databinding.FragmentEditProfileBinding
import com.c3.mobileapps.utils.CustomSnackbar
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by inject()
    private val snackbar = CustomSnackbar()
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var image_value: File

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
            // Hilangkan Fokus Keyboard
            hideKeyboard()

            // Get Data from Form Edit Profile
            val name    = binding.inputUsername.text.toString()
            val email   = binding.inputEmail.text.toString()
            val telp    = binding.inputTlp.text.toString()
            // Image Values has declared in Top of Initiliazer

            // Verified Data

            // Wrap to Dataclass
            val modelData = EditUser(name,email,telp,image_value)

            // Send Data to API
            editProfileViewModel.setUpdateUser(modelData)

            // Get Response from API
            editProfileViewModel.userResp.observe(viewLifecycleOwner){
                val res = it.data?.message.toString()

                // Display to Snackbar or Log
                when (it.status) {
                    Status.SUCCESS -> {
                        snackbar.showSnackbarUtils("Data Berhasil diperbarui",false, layoutInflater, requireView(), requireContext())
                        Log.i("Edit Profile Issues", res)
                        findNavController().navigate(R.id.profileFragment)
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

        binding.materialCardView.setOnClickListener {
            pickImageFromGallery()
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

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun saveImageToInternalStorage(selectedImageUri: Uri): File? {
        val imageStream = activity?.contentResolver?.openInputStream(selectedImageUri)
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "LEARNIFY_PROFILE_" + timeStamp + "_"
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        try {
            val imageFile = File.createTempFile(
                imageFileName, ".jpg", storageDir
            )

            val outStream: OutputStream = FileOutputStream(imageFile)
            val buffer = ByteArray(4 * 1024) // or other buffer size
            var bytesRead: Int
            while (imageStream?.read(buffer).also { bytesRead = it!! } != -1) {
                outStream.write(buffer, 0, bytesRead)
            }
            outStream.flush()
            outStream.close()
            imageStream?.close()

            return imageFile
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun saveImageFromUrlToInternalStorage(imageUrl: String): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "LEARNIFY_PROFILE_" + timeStamp + "_"
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        try {
            val imageFile = File.createTempFile(
                imageFileName, ".jpg", storageDir
            )

            val url = URL(imageUrl)
            val connection = url.openConnection()
            connection.connect()

            val inputStream = connection.getInputStream()
            val outputStream: OutputStream = FileOutputStream(imageFile)
            val buffer = ByteArray(4 * 1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()

            return imageFile
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            binding.imgProfile.setImageURI(selectedImageUri)
            image_value = selectedImageUri?.let { saveImageToInternalStorage(it) }!!
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}