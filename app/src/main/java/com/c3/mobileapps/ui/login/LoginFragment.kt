package com.c3.mobileapps.ui.login

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
import com.c3.mobileapps.databinding.FragmentLoginBinding
import com.c3.mobileapps.databinding.ItemCustomSnackbarBinding
import com.c3.mobileapps.ui.home.HomeFragment
import com.c3.mobileapps.ui.register.RegisterFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import java.lang.Exception
import java.security.MessageDigest

class LoginFragment : Fragment() {
	private lateinit var binding: FragmentLoginBinding
	private lateinit var customSnackbarBinding: ItemCustomSnackbarBinding
	private val loginViewModel: LoginViewModel by inject()

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

		// Some Logic Login Here
		binding.btnLogin.setOnClickListener {
			// Mengaktifkan ProgressBar
			binding.constraintLogin.visibility = View.VISIBLE

			// Ambil nilai dari Form
			val email  = binding.etEmail.text
			val pass   = binding.etPassword.text

			try {
				loginViewModel.login(email.toString(),pass.toString())
				loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer {res ->

					when (res.code()) {
						200 -> {
							showSnackbar("Login Berhasil!", false)

							// Intent to Homepage
							findNavController().navigate(R.id.homeFragment)
						}

						400 -> {
							showSnackbar("Email dan Password diperlukan!", true)
						}

						401 -> {
							showSnackbar("Email atau Password salah!", true)
						}

						500 -> {
							showSnackbar("Aplikasi dalam perbaikan. Mohon Coba Lagi", true)
						}
					}

					binding.constraintLogin.visibility = View.GONE
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
	}

	// Additional Function
	private fun showSnackbar(message: String?, error: Boolean) {
		val customSnackbarBinding = ItemCustomSnackbarBinding.inflate(layoutInflater)
		val customSnackbarView = customSnackbarBinding.root

		// Set teks pada Snackbar kustom
		customSnackbarBinding.tvMessage.text = message.toString()

		// Create a Snackbar with the root view of the fragment
		val snackbar = Snackbar.make(requireView(), "", Snackbar.LENGTH_SHORT)
		val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

		// Set warna latar belakang Snackbar
		if (error) {
			customSnackbarBinding.layoutSnackbar.background = ColorDrawable(Color.parseColor("#F31559"))
		} else {
			customSnackbarBinding.ivLogo.setImageDrawable(resources.getDrawable(R.drawable.check_circle_24, requireContext().theme))
			customSnackbarBinding.layoutSnackbar.background = ColorDrawable(Color.parseColor("#8ADAB2"))
		}
		snackbarLayout.setPadding(0,0,0,0)

		// Add the custom view to SnackbarLayout
		snackbarLayout.addView(customSnackbarView, 0)
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
}