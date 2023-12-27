package com.c3.mobileapps.ui.register

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.databinding.FragmentRegister1Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
//import .RegisterSuccessBottomSheet

@Suppress("SameParameterValue")
class RegisterFragment : Fragment() {
	private lateinit var binding: FragmentRegister1Binding
	private val registerViewModel: RegisterViewModel by inject()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		onAttach(requireContext())
		binding = FragmentRegister1Binding.inflate(inflater, container, false)
		return  binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		onAttach(requireContext())

		binding.btnDaftar.setOnClickListener{
			// Aktifkan ProgressBar
			binding.constraintRegister.visibility = View.VISIBLE

			val name    = binding.etNama.text.toString()
			val email   = binding.etEmail.text.toString()
			val noTelp  = binding.etNoTelp.text.toString()
			val pass    = binding.etPassword.text.toString()

			// Adding Some check data (Additional)

			// Pemeriksaan Panjang Nomor (9-13 Digit)
			if (noTelp.length < 10 || noTelp.length > 13 ) {
				showSnackbar("Nomor Telepon Anda tidak sesuai! ", true)
			} else {
				val verifiedPhone = checkCharacter(noTelp)

//			    Wrap to Dataclass
				val modelData = RegisterRequest(
					email = email,
					name = name,
					password = pass,
					phone_number = verifiedPhone
				)

				registerViewModel.sendData(modelData)
				registerViewModel.registerResponse.observe(viewLifecycleOwner, Observer {res ->

					when (res.code()) {
						200 -> {
							// Intent to Loginpage
							findNavController().navigate(R.id.loginFragment)
						}

						400 -> {
							showSnackbar("Validasi Error", true)
						}

						500 -> {
							showSnackbar("Aplikasi dalam perbaikan. Mohon Coba Lagi", true)
						}
					}

					binding.constraintRegister.visibility = View.VISIBLE
				})
			}
		}

		binding.ivBack.setOnClickListener {
			findNavController().popBackStack()
		}

		binding.tvToLogin.setOnClickListener {
			findNavController().popBackStack()
		}
	}

	// Additional Function
	private fun checkCharacter(number: String): String{
		val firstChar = number[0]
		var modifiedText = number

		// Mengecek apakah karakter pertama adalah "0"
		if (firstChar == '0') {
			// Menghapus karakter pertama
			modifiedText = number.substring(1)
		}

		// Menambahkan "+62" di awal
		val finalText = "+62$modifiedText"

		return finalText
	}

	private fun showSnackbar(message: String?, error: Boolean) {
		val snackbar = Snackbar.make(requireView(), message.toString(), Snackbar.LENGTH_SHORT)

		// Set Background Snackbar
		if (error) {
			snackbar.view.setBackgroundColor(Color.parseColor("#F31559"))
		} else {
			snackbar.view.setBackgroundColor(Color.parseColor("#8ADAB2"))
		}

		snackbar.show()
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

//	private fun showBottomSheet() {
//		val bottomSheet = RegisterSuccessBottomSheet()
//		bottomSheet.show(parentFragmentManager, bottomSheet.tag)
//	}
}