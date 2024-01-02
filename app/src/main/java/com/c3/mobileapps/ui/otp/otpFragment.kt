package com.c3.mobileapps.ui.otp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.data.remote.model.request.auth.OtpRequest
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.databinding.FragmentOtpBinding
import com.c3.mobileapps.ui.login.LoginViewModel
import com.c3.mobileapps.utils.CustomSnackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class OtpFragment : Fragment() {
	private lateinit var binding: FragmentOtpBinding
	private val loginViewModel: LoginViewModel by inject()
	private val otpViewModel: OtpViewModel by inject()
	private val sharedPreferences: SharedPref by inject()
	private val snackbar = CustomSnackbar() // Custom Snackbar Object Class

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		onAttach(requireContext())
		binding = FragmentOtpBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		onAttach(requireContext())

		val registerData: RegisterRequest? = arguments?.getParcelable("RegisterModel")
		val email   = registerData?.email.toString()
		val pass    = registerData?.password.toString()

		// Observe Text Length
		binding.etOTP.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
				// Let it Blank!
			}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
				val kodeOTP = binding.etOTP.text

				if(kodeOTP.length == 6){
					// Hilangkan keyboard
					hideKeyboard()

					binding.constraintOTP.visibility = View.VISIBLE

					// Wrap into Dataclass
					val modelData = OtpRequest(email,kodeOTP.toString())

					otpViewModel.verifyOTP(modelData)
					otpViewModel.otpResponse.observe(viewLifecycleOwner, Observer {res ->
						when (res.code()) {
							200 -> {
								// Notify the user
								snackbar.showSnackbarUtils("Verifikasi Berhasil! Sedang Mengarahkanmu ke Beranda..", false, layoutInflater, requireView(), requireContext())

								// Logging new User
								loginViewModel.login(email,null,pass)

								// Handle Login Response
								loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { res ->

									Log.d("Login Response", res.code().toString())

									when (res.code()){
										200 -> {
											snackbar.showSnackbarUtils("Login Berhasil!", false, layoutInflater, requireView(), requireContext())

											val data = res.body()?.data

											sharedPreferences.setIsLogin(true)
											sharedPreferences.setToken("Bearer ${data?.token}")

											// Intent to Homepage
											findNavController().navigate(R.id.homeFragment)
										}

										500 -> {
											snackbar.showSnackbarUtils("Aplikasi dalam perbaikan. Mohon Coba Lagi!", true, layoutInflater, requireView(), requireContext())
										}
									}
								})

							}

							401 -> {
								snackbar.showSnackbarUtils("Kode OTP Tidak Cocok", true, layoutInflater, requireView(), requireContext())
							}

							500 -> {
								snackbar.showSnackbarUtils("Aplikasi dalam perbaikan. Mohon Coba Lagi", true, layoutInflater, requireView(), requireContext())
							}
						}

						// Finish Loading
						binding.constraintOTP.visibility = View.INVISIBLE
					})
				}
			}

			override fun afterTextChanged(p0: Editable?) {
				// Let it Blank!
			}

		})
	}

	private fun Fragment.hideKeyboard() {
		view?.let { activity?.hideKeyboard(it) }
	}

	private fun Context.hideKeyboard(view: View) {
		val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		val bottomNavigationView: BottomNavigationView? = activity?.findViewById(R.id.bottom_navigation)
		bottomNavigationView?.visibility = View.GONE
	}

	override fun onDetach() {
		super.onDetach()
		val bottomNavigationView: BottomNavigationView? = activity?.findViewById(R.id.bottom_navigation)
		bottomNavigationView?.visibility = View.VISIBLE
	}

}