package com.c3.mobileapps.ui.login

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.local.SharedPref
import com.c3.mobileapps.databinding.FragmentLoginBinding
import com.c3.mobileapps.ui.customAlertDialog.ProgressBarDialog
import com.c3.mobileapps.ui.main_activity.MainViewModel
import com.c3.mobileapps.utils.CustomSnackbar
import com.c3.mobileapps.utils.Status
import com.c3.mobileapps.utils.ValidasiHelper.checkCharacter
import com.c3.mobileapps.utils.ValidasiHelper.isValidInput
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import org.koin.android.ext.android.inject
import java.lang.Exception

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by inject()
    private val mainViewModel: MainViewModel by inject()
    private val sharedPreferences: SharedPref by inject()
    private val snackbar = CustomSnackbar() // Custom Snackbar Object Class

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        onAttach(requireContext())
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onAttach(requireContext())

        binding.constraintLogin.visibility = View.INVISIBLE
        val isLogin = sharedPreferences.getIsLogin()

        if (isLogin) {
            findNavController().navigate(R.id.homeFragment)
        }

        validasiInput()

        binding.btnLogin.setOnClickListener {
            makeLogin()
        }

        binding.tvToRegister.setOnClickListener {
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

    private fun makeLogin() {
        // Hilangkan Fokus keyboard setelah tekan tombol
        hideKeyboard()

        // Ambil nilai dari Form
        val username = binding.etEmail.text.toString().trim()
        val pass = binding.etPassword.text.toString().trim()

        try {
            // Cek email or phone number is empty or not
            binding.constraintLogin.visibility = View.VISIBLE

            if (Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                loginViewModel.login(username, null, pass)
            } else if (username.matches(Regex("^(0|62|\\+62)\\d+$"))) {
                val phoneNUmber = checkCharacter(username)
                loginViewModel.login(null, phoneNUmber, pass)
            }

            loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer { res ->
                when (res.code()) {
                    200 -> {
                        snackbar.showSnackbarUtils(
                            "Login Berhasil!",
                            false,
                            layoutInflater,
                            requireView(),
                            requireContext()
                        )

                        val data = res.body()?.data

                        sharedPreferences.setIsLogin(true)
                        sharedPreferences.setToken("Bearer ${data?.token}")
                        getNotif()
                        // Intent to Homepage
                        findNavController().navigate(R.id.homeFragment)
                        binding.constraintLogin.visibility = View.INVISIBLE
                    }

                    400 -> {
                        snackbar.showSnackbarUtils(
                            "Email dan Password diperlukan!",
                            true,
                            layoutInflater,
                            requireView(),
                            requireContext()
                        )
                        binding.constraintLogin.visibility = View.INVISIBLE
                    }

                    401 -> {
                        snackbar.showSnackbarUtils(
                            "Email atau Password salah!",
                            true,
                            layoutInflater,
                            requireView(),
                            requireContext()
                        )
                        binding.constraintLogin.visibility = View.INVISIBLE
                    }

                    404 -> {
                        snackbar.showSnackbarUtils(
                            "Akun Tidak Ditemukan!",
                            true,
                            layoutInflater,
                            requireView(),
                            requireContext()
                        )
                        binding.constraintLogin.visibility = View.INVISIBLE
                    }

                    500 -> {
                        snackbar.showSnackbarUtils(
                            "Aplikasi dalam perbaikan. Mohon Coba Lagi!",
                            true,
                            layoutInflater,
                            requireView(),
                            requireContext()
                        )
                        binding.constraintLogin.visibility = View.INVISIBLE
                    }
                }
                binding.constraintLogin.visibility = View.INVISIBLE
            })

        } catch (e: Exception) {
            Log.e("Auth Issues", e.toString())
        }
    }

    private fun alertDialog(view: View) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alert_reset_password_layout, null)
        val editText = dialogLayout.findViewById<TextInputEditText>(R.id.inputResetPass)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Kirim Email") { dialogInterface, i ->
            sendResetPassword(requireView(), editText.text.toString())
            // Hilangkan Fokus keyboard setelah tekan tombol
            hideKeyboard()
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

            when (res.code()) {
                201 -> {
                    snackbar.showSnackbarUtils(
                        "Link Reset Password telah dikirim! Cek Email, ya?",
                        false,
                        layoutInflater,
                        requireView(),
                        requireContext()
                    )
                }

                404 -> {
                    snackbar.showSnackbarUtils(
                        "Akun tidak ditemukan. Coba Lagi!",
                        true,
                        layoutInflater,
                        requireView(),
                        requireContext()
                    )
                }
            }

            // Finish ProgressBar
            progressBarDialog.dismiss()
        })
    }

    private fun validasiInput() {

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (text.isEmpty()) {
                    binding.layoutEmail.error = "Email atau Nomor Telepon Diperlukan"
                    binding.etEmail.requestFocus()

                } else if (!isValidInput(text.toString())) {
                    binding.layoutEmail.error = "Format Email atau Nomor Telepon Tidak Valid!"
                    binding.etEmail.requestFocus()
                } else {
                    binding.layoutEmail.error = null
                }
            }

            override fun afterTextChanged(text: Editable) {}
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (text.toString().isEmpty()) {
                    binding.layoutPassword.error = "Password Diperlukan!"
                    binding.etPassword.requestFocus()
                } else if (text.toString().length < 8) {
                    binding.layoutPassword.error = "Password minimal 8 Karakter!"
                    binding.etPassword.requestFocus()
                } else {
                    binding.layoutPassword.error = null
                }
            }

            override fun afterTextChanged(text: Editable) {}
        })

    }

    private fun getNotif() {
        mainViewModel.getListNotif()
        mainViewModel.notifResp.observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Status.SUCCESS -> {

                    val unViewed = res.data?.data?.count {
                        it.viewed == false
                    }

                    if (unViewed != 0){
                        val bottomNavigationView: BottomNavigationView? =
                            activity?.findViewById(R.id.bottom_navigation)
                        val badge = bottomNavigationView?.getOrCreateBadge(R.id.notificationFragment)
                        badge?.number = unViewed!!
                    }


                }

                Status.LOADING -> {
                }

                Status.ERROR -> {
                }
            }
        }
    }


    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}