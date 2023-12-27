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

        val course = arguments?.getParcelable<Course>("COURSE")
        val payment = arguments?.getParcelable<Payment>("PAYMENT")

        setView(course)


        binding.metode1.setOnClickListener {
            binding.cardTransfer.visibility = View.VISIBLE
            binding.cardCreditPayment.visibility = View.GONE
            binding.edtNoRek.keyListener = null
            binding.edtAtasNama.keyListener = null
            binding.edtNamaRekening.keyListener = null
            binding.metode1.setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.primary))
            binding.metode2.setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
            paymentMethod = "BANK_TRANSFER"
        }

        binding.metode2.setOnClickListener {
            binding.cardTransfer.visibility = View.GONE
            binding.cardCreditPayment.visibility = View.VISIBLE
            binding.metode2.setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.primary))
            binding.metode1.setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red))
            paymentMethod = "CREDIT_CARD"
        }

        binding.btnPayment.setOnClickListener {
            Log.e("Payment", course.toString())
            if (paymentMethod == "BANK_TRANSFER"){
                createPayment(payment?.id.toString())
            }else{
                validatePayment(payment?.id.toString())
            }

//
        }



        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setView(course: Course?) {
        binding.tvNamaKelas.text = course?.courseCategory?.name
        binding.deskripsiJudulKelas.text = course?.name
        binding.creatorKelas.text = "by ${course?.author}"
        binding.rating.text = course?.rating

        val price = course?.price
        binding.tvHarga.text = price?.formatAsPrice()

        val ppn = price?.times(0.11)
        binding.tvPpn.text = ppn?.formatAsPrice()

        val total = ppn?.let { price.plus(it) }
        binding.total.text = total?.formatAsPrice()
        Glide.with(binding.root)
            .load(course?.image ?: course?.courseCategory?.image)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageView)

    }

    private fun validatePayment(id:String) {
        binding.apply {
            if (edtCardNumber.text.isNullOrEmpty()){
                edtCardNumber.error = "Card number cannot be empty"
                edtCardNumber.requestFocus()
            }else if (edtCardName.text.isNullOrEmpty()){
                edtCardName.error = "Card name cannot be empty"
                edtCardName.requestFocus()
            }else if (etCvv.text.isNullOrEmpty()){
                etCvv.error = "CVV cannot be empty"
                etCvv.requestFocus()
            }else if (etExpired.text.isNullOrEmpty()){
                etExpired.error = "CVV cannot be empty"
                etExpired.requestFocus()
            }else{
                createPayment(paymentId = id)
            }
        }


    }

    private fun createPayment(paymentId: String) {
        lifecycleScope.launch {
            paymentViewModel.updateStatus(paymentId, StatusRequest(paymentMethod))
            paymentViewModel.paymentResp.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.pbLoading.visibility = View.GONE
                        //show success payment
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

