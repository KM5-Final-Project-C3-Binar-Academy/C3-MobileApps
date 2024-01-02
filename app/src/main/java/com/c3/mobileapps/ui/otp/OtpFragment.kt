package com.c3.mobileapps.ui.otp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.request.auth.OtpRequest
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.databinding.FragmentOtpBinding
import com.c3.mobileapps.utils.CustomSnackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class OtpFragment : Fragment() {
	private lateinit var binding: FragmentOtpBinding
	private val otpViewModel: OtpViewModel by inject()
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
		val nama    = registerData?.name.toString()

		// Observe Text Length
		binding.etOTP.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
				// Let it Blank!
			}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
				val kodeOTP = binding.etOTP.text

				if(kodeOTP.length == 6){
					binding.constraintOTP.visibility = View.VISIBLE

					Log.i("OTP Issues", email)
					Log.i("OTP Issues", kodeOTP.toString())

					// Wrap into Dataclass
					val modelData = OtpRequest(email,kodeOTP.toString())

					otpViewModel.verifyOTP(modelData)
					otpViewModel.otpResponse.observe(viewLifecycleOwner, Observer {res ->
						when (res.code()) {
							200 -> {
								snackbar.showSnackbarUtils("Verifikasi Berhasil! Selamat Datang, $nama", false, layoutInflater, requireView(), requireContext())
								// Intent to Loginpage
								findNavController().navigate(R.id.homeFragment)
							}

							400 -> {
								snackbar.showSnackbarUtils("Validasi Error", true, layoutInflater, requireView(), requireContext())
							}

							500 -> {
								snackbar.showSnackbarUtils("Aplikasi dalam perbaikan. Mohon Coba Lagi", true, layoutInflater, requireView(), requireContext())
							}
						}
					})

					binding.constraintOTP.visibility = View.INVISIBLE
				}
			}

			override fun afterTextChanged(p0: Editable?) {
				// Let it Blank!
			}

		})
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