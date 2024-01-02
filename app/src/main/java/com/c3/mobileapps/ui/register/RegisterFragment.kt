package com.c3.mobileapps.ui.register

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.request.auth.RegisterRequest
import com.c3.mobileapps.databinding.FragmentRegister1Binding
import com.c3.mobileapps.utils.CustomSnackbar
import com.c3.mobileapps.utils.ValidasiHelper
import com.c3.mobileapps.utils.ValidasiHelper.checkCharacter
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

@Suppress("SameParameterValue")
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegister1Binding
    private val registerViewModel: RegisterViewModel by inject()
    private val snackbar = CustomSnackbar() // Custom Snackbar Object Class

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        onAttach(requireContext())
        binding = FragmentRegister1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onAttach(requireContext())

        binding.btnDaftar.setOnClickListener {
            // Hilangkan fokus keyboard
            hideKeyboard()

            val name = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val noTelp = binding.etNoTelp.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()
            val confirmpass = binding.etConfirmPassword.text.toString().trim()

            // Adding Some check data (Additional)
            if (name.isEmpty()) {
                binding.layoutUsername.error = "Nama Diperlukan"
                binding.etNama.requestFocus()

            } else if (email.isEmpty()) {
                binding.layoutEmail.error = "Email Diperlukan"
                binding.etEmail.requestFocus()

            } else if (!ValidasiHelper.isValidInput(email)) {
                binding.layoutEmail.error = "Format Email Tidak Valid!"
                binding.etEmail.requestFocus()
            } else if (noTelp.isEmpty()) {
                binding.layoutTlp.error = "Email Diperlukan"
                binding.etNoTelp.requestFocus()

            } else if (!ValidasiHelper.isValidInput(noTelp)) {
                binding.layoutTlp.error = "Format Nomor Telepon Tidak Valid!"
                binding.etNoTelp.requestFocus()
            } else if (pass.isEmpty()) {
                binding.layoutPassword.error = "Password Diperlukan!"
                binding.etPassword.requestFocus()
            } else if (pass.length < 8) {
                binding.layoutPassword.error = "Password minimal 8 Karakter!"
                binding.etPassword.requestFocus()
            } else if (pass != confirmpass) {
                binding.layoutPassword.error = "Confirm Password dan Password Tidak Sesuai"
                binding.etPassword.requestFocus()
            } else {
                // Aktifkan ProgressBar
                binding.constraintRegister.visibility = View.VISIBLE
                val verifiedPhone = checkCharacter(noTelp)

//			    Wrap to Dataclass
                val modelData = RegisterRequest(
                    email = email,
                    name = name,
                    password = confirmpass,
                    phone_number = verifiedPhone
                )

                val bundle = Bundle().apply {
                    putParcelable("RegisterModel", modelData)
                }


                registerViewModel.sendData(modelData)
                registerViewModel.registerResponse.observe(viewLifecycleOwner, Observer { res ->

                    when (res.code()) {
                        201 -> {
                            snackbar.showSnackbarUtils(
                                "Pendaftaran Berhasil! Silahkan cek kode OTP di Email!",
                                false,
                                layoutInflater,
                                requireView(),
                                requireContext()
                            )
                            // Intent to Loginpage
                            findNavController().navigate(R.id.otpFragment, bundle)
                        }

                        400 -> {
                            snackbar.showSnackbarUtils(
                                "Validasi Error",
                                true,
                                layoutInflater,
                                requireView(),
                                requireContext()
                            )
                        }

                        500 -> {
                            snackbar.showSnackbarUtils(
                                "Aplikasi dalam perbaikan. Mohon Coba Lagi",
                                true,
                                layoutInflater,
                                requireView(),
                                requireContext()
                            )
                        }
                    }

                    binding.constraintRegister.visibility = View.GONE
                })

                //tempat menyimpan tutup kurung
            }
        }

        binding.ivBack.setOnClickListener {
            // Gunakan Navigate alih-alih popStackback untuk menghindari tampilnya BottomNavigation
            findNavController().navigate(R.id.loginFragment)
        }

        binding.tvToLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    // Additional Function

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}