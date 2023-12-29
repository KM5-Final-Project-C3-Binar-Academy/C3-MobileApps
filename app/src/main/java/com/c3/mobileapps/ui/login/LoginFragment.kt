package com.c3.mobileapps.ui.login

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.databinding.FragmentLoginBinding
import com.c3.mobileapps.databinding.ItemCustomSnackbarBinding
import com.c3.mobileapps.ui.home.HomeFragment
import com.c3.mobileapps.ui.register.RegisterFragment
import com.c3.mobileapps.utils.CustomSnackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import org.koin.android.ext.android.inject
import java.lang.Exception
import java.security.MessageDigest

class LoginFragment : Fragment() {
	private lateinit var binding: FragmentLoginBinding
	private lateinit var customSnackbarBinding: ItemCustomSnackbarBinding
	private val loginViewModel: LoginViewModel by inject()
	private val sharedPreferences: SharedPref by inject()
	private val snackbar = CustomSnackbar() // Custom Snackbar Object Class

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		onAttach(requireContext())
		binding = FragmentLoginBinding.inflate(inflater,container,false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		onAttach(requireContext())

		binding.constraintLogin.visibility = View.INVISIBLE
		val isLogin = sharedPreferences.getIsLogin()

		if (isLogin){
			findNavController().navigate(R.id.homeFragment)
		}

		// Some Logic Login Here
		binding.btnLogin.setOnClickListener {
			// Ambil nilai dari Form
			val email  = binding.etEmail.text
			val pass   = binding.etPassword.text

			try {
				// Mengaktifkan ProgressBar
				binding.constraintLogin.visibility = View.VISIBLE
				loginViewModel.login(email.toString(),pass.toString())
				loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer {res ->

					when (res.code()) {
						200 -> {
							snackbar.showSnackbarUtils("Login Berhasil!", false, layoutInflater, requireView(), requireContext())

							val data = res.body()?.data

							sharedPreferences.setIsLogin(true)
							sharedPreferences.setToken("Bearer ${data?.token}")
							// Intent to Homepage
							findNavController().navigate(R.id.homeFragment)
						}

						400 -> {
							snackbar.showSnackbarUtils("Email dan Password diperlukan!", true, layoutInflater, requireView(), requireContext())
						}

						401 -> {
							snackbar.showSnackbarUtils("Email atau Password salah!",true, layoutInflater,requireView(),requireContext())
						}
						404 -> {
							snackbar.showSnackbarUtils("Akun Tidak Ditemukan!", true, layoutInflater,requireView(),requireContext())
						}

						500 -> {
							snackbar.showSnackbarUtils("Aplikasi dalam perbaikan. Mohon Coba Lagi!", true, layoutInflater, requireView(), requireContext())
						}
					}

					binding.constraintLogin.visibility = View.INVISIBLE
				})
			} catch (e: Exception) {
				Log.e("Auth Issues", e.toString())
			}
		}

			binding.tvToRegister.setOnClickListener{
				findNavController().navigate(R.id.registerFragment)
			}

			binding.tvBypass.setOnClickListener {
				// Intent to Homepage
				findNavController().navigate(R.id.homeFragment)
			}

		binding.tvResetPass.setOnClickListener {
			alertDialog(requireView())
		}
	}

	// Additional Function
	private fun alertDialog(view: View){
		val builder = AlertDialog.Builder(requireContext())
		val inflater = layoutInflater
		val dialogLayout = inflater.inflate(R.layout.alert_reset_password_layout, null)
		val editText  = dialogLayout.findViewById<TextInputEditText>(R.id.inputResetPass)

		builder.setView(dialogLayout)
		builder.setPositiveButton("Kirim Email") {dialogInterface, i ->
			snackbar.showSnackbarUtils(editText.text.toString(), false, layoutInflater,requireView(),requireContext())
		}
		builder.show()
	}

//	private fun sendResetPassword(view: View) {
//		// Starting ProgressBar
//		binding.constraintLogin.visibility = View.VISIBLE
//
//		// Kirim data ke viewModel
//
//
//		// Handle responsenya
//		// Finish ProgressBar
//		binding.constraintLogin.visibility = View.INVISIBLE
//	}

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