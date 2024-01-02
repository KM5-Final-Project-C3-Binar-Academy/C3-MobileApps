package com.c3.mobileapps.ui.editPassword

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.request.user.EditPassword
import com.c3.mobileapps.data.remote.model.response.user.User
import com.c3.mobileapps.databinding.FragmentEditPasswordBinding
import com.c3.mobileapps.utils.CustomSnackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class EditPasswordFragment : Fragment() {
	private val editPasswordViewModel : EditPasswordViewModel by inject()
	private lateinit var binding : FragmentEditPasswordBinding
	private var snackbar = CustomSnackbar()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		onAttach(requireContext())
		binding = FragmentEditPasswordBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.constraintedtPass.visibility = View.INVISIBLE

		editPassword()

		binding.ivBack.setOnClickListener {
			findNavController().popBackStack()
		}
	}

	private fun editPassword() {
		binding.apply {
			btnUbahPassword.setOnClickListener{
				// Get Data from EditText
				val oldPassword = binding.inputOldPassword.text.toString()
				val newPassword = binding.inputNewPassword.text.toString()
				val cekPassword = binding.inputConfirmPassword.text.toString()

				if(inputOldPassword.text.isNullOrEmpty()){
					inputOldPassword.error = "Data Tidak Boleh Kosong"
					inputOldPassword.requestFocus()
				} else if(inputNewPassword.text.isNullOrEmpty()){
					inputNewPassword.error = "Data Tidak Boleh Kosong"
					inputNewPassword.requestFocus()
				} else if(inputConfirmPassword.text.isNullOrEmpty()){
					inputConfirmPassword.error = "Data Tidak Boleh Kosong"
					inputConfirmPassword.requestFocus()
				} else if(newPassword != cekPassword){
					inputConfirmPassword.error = "Password Baru Tidak Sesuai"
					inputConfirmPassword.requestFocus()
				}else{
					// Verification That New Password is Correctly
					hideKeyboard()
					// Starting Loading...
					binding.constraintedtPass.visibility = View.VISIBLE
					// Wrap into Dataclass
					val modelData = EditPassword(oldPassword,newPassword)
					// send data into ViewModel
					editPasswordViewModel.editPass(modelData)
					// Handle response in API Server
					editPasswordViewModel.edtPassResp.observe(viewLifecycleOwner, Observer {res ->
						when (res.code()) {
							200 -> {
								snackbar.showSnackbarUtils("Berhasil Mengganti Password", false, layoutInflater, requireView(), requireContext())
								findNavController().navigate(R.id.loginFragment)
							}

							400 -> {
								snackbar.showSnackbarUtils("Terjadi Kesalahan. Coba Lagi!", true , layoutInflater, requireView(), requireContext())
							}

							401 -> {
								snackbar.showSnackbarUtils("Password Lama tidak sesuai!", true , layoutInflater, requireView(), requireContext())
							}

							419 -> {
								snackbar.showSnackbarUtils("Password baru tidak boleh sama!", true , layoutInflater, requireView(), requireContext())
							}
						}

						// Finish Loading..
						binding.constraintedtPass.visibility = View.INVISIBLE
					})
				}

			}
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