package com.c3.mobileapps.ui.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.request.user.EditUser
import com.c3.mobileapps.data.remote.model.response.user.User
import com.c3.mobileapps.databinding.FragmentEditProfileBinding
import com.c3.mobileapps.utils.CustomSnackbar
import com.c3.mobileapps.utils.MediaHelper.bitmapToUri
import com.c3.mobileapps.utils.MediaHelper.reduceFileImage
import com.c3.mobileapps.utils.MediaHelper.uriToFile
import com.c3.mobileapps.utils.Status
import org.koin.android.ext.android.inject


class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by inject()
    private val snackbar = CustomSnackbar()
    private var currentUri: Uri? = null

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
        val currentUser = arguments?.getParcelable<User>("PROFILE")
        getCurrentUser(currentUser)

        binding.imgProfile.setOnClickListener {
            checkPermissions()
        }

        binding.btnSimpan.setOnClickListener {
            // Hilangkan Fokus Keyboard
            hideKeyboard()

            val name = binding.inputUsername.text.toString().trim()
            val email = binding.inputEmail.text.toString().trim()
            val telp = binding.inputTlp.text.toString().trim()

            currentUri.let { newImage ->

                if (newImage != null){
                    // Get Data from Form Edit Profile
                    val imageFile = uriToFile(newImage, requireContext()).reduceFileImage()
                    val modelData = EditUser(name, email, telp, null)
                    editProfileViewModel.updateUser(modelData, imageFile)
                }else{
                    val modelData = EditUser(name, email, telp, currentUser?.image)
                    editProfileViewModel.updateUserWithoutImage(modelData)
                }
            }

            editProfileViewModel.userResp.observe(viewLifecycleOwner) {
                val res = it.data?.message.toString()

                // Display to Snackbar or Log
                when (it.status) {
                    Status.SUCCESS -> {
                        snackbar.showSnackbarUtils(
                            "Data Berhasil diperbarui",
                            false,
                            layoutInflater,
                            requireView(),
                            requireContext()
                        )
                        Log.i("Edit Profile Issues", res)
                        findNavController().navigate(R.id.profileFragment)
                    }

                    Status.ERROR -> {
                        snackbar.showSnackbarUtils(
                            res,
                            true,
                            layoutInflater,
                            requireView(),
                            requireContext()
                        )
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
        }
    }

    private fun getCurrentUser(currentUser: User?) {

        currentUser?.image?.let { imageUrl ->
            Glide.with(requireContext())
                .load(imageUrl)
                .into(binding.imgProfile)
        }

        binding.inputUsername.setText(currentUser?.name)
        binding.inputEmail.setText(currentUser?.email)
        binding.inputTlp.setText(currentUser?.phoneNumber)
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext()).setMessage("Pilih Gambar")
            .setPositiveButton("Galeri") { _, _ -> openGallery() }
            .setNegativeButton("Kamera") { _, _ -> openCamera() }.show()
    }

    private fun checkPermissions() {
        if (isGranted(
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun openGallery() {
        val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        imageIntent.type = "image/*"
        imageIntent.action = Intent.ACTION_GET_CONTENT
        galleryResult.launch(imageIntent.type)
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            Log.i("TEST GALERY", result.toString())
            if (result != null) {
                Glide.with(requireContext()).load(result)
                    .into(binding.imgProfile)
            }
        }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }

    private fun handleCameraImage(intent: Intent?) {
        Log.e("FOTO", intent?.extras?.get("data").toString())
        val bitmap = intent?.extras?.get("data") as Bitmap

        val uri = bitmapToUri(requireContext(),bitmap)
        Log.e("FOTO",uri.toString())
        Glide.with(requireContext()).load(bitmap)
            .into(binding.imgProfile)

        currentUri = uri
    }

    private fun isGranted(
        permission: String, //for camera
        permissions: Array<String>, //for read write storage/gallery
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(requireContext(), permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    permission
                )
            ) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission denied")
            .setMessage("Permission is denied, Please allow app permission from App Settings")
            .setPositiveButton("App Settings") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", "packageName", null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 3
        private const val GALLERY_RESULT_CODE = 15
    }
}