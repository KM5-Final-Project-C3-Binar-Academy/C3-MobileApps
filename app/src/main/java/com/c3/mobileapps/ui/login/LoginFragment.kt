package com.c3.mobileapps.ui.login

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.databinding.FragmentLoginBinding
import com.c3.mobileapps.ui.customAlertDialog.ProgressBarDialog
import com.c3.mobileapps.utils.CustomSnackbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.lang.Exception

class LoginFragment : Fragment() {
	private lateinit var binding: FragmentLoginBinding
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
			// Hilangkan Fokus keyboard setelah tekan tombol
			hideKeyboard()

			// Ambil nilai dari Form
			val email  = binding.etEmail.text
			val pass   = binding.etPassword.text

			// Tambah pengecekan (dia email atau nomor telepon)
			// Patterns.EMAIL_ADDRESS.matcher(text).matches()

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
							binding.constraintLogin.visibility = View.INVISIBLE
						}

						400 -> {
							snackbar.showSnackbarUtils("Email dan Password diperlukan!", true, layoutInflater, requireView(), requireContext())
							binding.constraintLogin.visibility = View.INVISIBLE
						}

						401 -> {
							snackbar.showSnackbarUtils("Email atau Password salah!",true, layoutInflater,requireView(),requireContext())
							binding.constraintLogin.visibility = View.INVISIBLE
						}
						404 -> {
							snackbar.showSnackbarUtils("Akun Tidak Ditemukan!", true, layoutInflater,requireView(),requireContext())
							binding.constraintLogin.visibility = View.INVISIBLE
						}

						500 -> {
							snackbar.showSnackbarUtils("Aplikasi dalam perbaikan. Mohon Coba Lagi!", true, layoutInflater, requireView(), requireContext())
							binding.constraintLogin.visibility = View.INVISIBLE
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
		val builder      = AlertDialog.Builder(requireContext())
		val inflater     = layoutInflater
		val dialogLayout = inflater.inflate(R.layout.alert_reset_password_layout, null)
		val editText     = dialogLayout.findViewById<TextInputEditText>(R.id.inputResetPass)

		builder.setView(dialogLayout)
		builder.setPositiveButton("Kirim Email") {dialogInterface, i ->
			sendResetPassword(requireView(), editText.text.toString())
		}
		builder.show()
	}

	private fun sendResetPassword(view: View, email: String) {
		val progressBarDialog = ProgressBarDialog(requireContext())

		// Kirim data ke viewModel
		loginViewModel.resetPassword(email)
		progressBarDialog.show()

		// Handle responsenya
		loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { res ->

			when(res.code()) {
				200 -> {
					snackbar.showSnackbarUtils("Link Reset Password telah dikirim! Cek Email, ya?", false, layoutInflater,requireView(),requireContext())
				}

				404 -> {
					snackbar.showSnackbarUtils("Akun tidak ditemukan. Coba Lagi!", true, layoutInflater, requireView(),requireContext())
				}
			}

			// Finish ProgressBar
			progressBarDialog.dismiss()
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