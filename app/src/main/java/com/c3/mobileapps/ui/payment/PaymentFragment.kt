package com.c3.mobileapps.ui.payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.databinding.FragmentPaymentBinding
import com.c3.mobileapps.utils.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private val paymentViewModel: PaymentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseId = arguments?.getString("COURSE_ID")


        binding.metode1.setOnClickListener {
            binding.cardTransfer.visibility = View.VISIBLE
            binding.cardCreditPayment.visibility = View.GONE
        }

        binding.metode2.setOnClickListener {
            binding.cardTransfer.visibility = View.GONE
            binding.cardCreditPayment.visibility = View.VISIBLE
        }

        binding.btnPayment.setOnClickListener {
            Log.e("Payment", courseId.toString())
            createPayment(courseId.toString())
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validatePayment() {

    }

    private fun createPayment(courseId: String) {
        lifecycleScope.launch {
            paymentViewModel.makePayment(courseId)
            paymentViewModel.paymentResp.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.pbLoading.visibility = View.GONE
                        val data = it.data?.data
                        val bundle = bundleOf("PAYMENT" to data)
                        findNavController().navigate(R.id.confirmPaymentFragment, bundle)
                    }

                    Status.LOADING -> {
                        binding.pbLoading.visibility = View.VISIBLE
                    }

                    Status.ERROR -> {
                        binding.pbLoading.visibility = View.GONE
                        Snackbar.make(binding.root, "Anda Sudah Bergabung Dengan Kelas", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red
                                )
                            )
                            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                            .show()
                        findNavController().popBackStack()

                    }

                }
            }
        }
    }
}

