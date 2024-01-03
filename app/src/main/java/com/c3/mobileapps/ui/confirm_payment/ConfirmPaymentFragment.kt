package com.c3.mobileapps.ui.confirm_payment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.data.remote.model.request.payment.StatusRequest
import com.c3.mobileapps.data.remote.model.response.payment.Payment
import com.c3.mobileapps.databinding.FragmentConfirmPaymentBinding
import com.c3.mobileapps.ui.payment.OnBoardingBottomSheet
import com.c3.mobileapps.utils.Status
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Locale


class ConfirmPaymentFragment : Fragment() {
    private lateinit var binding: FragmentConfirmPaymentBinding
    private val cfrmPaymentViewModel: CfrmPaymentViewModel by inject()

    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding= FragmentConfirmPaymentBinding.inflate(inflater, container, false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paymentData: Payment? = arguments?.getParcelable("PAYMENT")

        val expiredAtDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(paymentData?.expiredAt)

        // Calculate the duration in milliseconds
        val durationMillis = expiredAtDate.time - System.currentTimeMillis()

        // Initialize and start the countdown timer
        timer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / (1000 * 60)) % 60
                val seconds = (millisUntilFinished / 1000) % 60

                val timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
                binding.tvInfoPayment.text = timeLeft
            }

            override fun onFinish() {
                // Update status to GAGAL when the timer finishes
//                updateStatusToGagal()
            }
        }.start()

        binding.btnAcceptPayment.setOnClickListener {
            lifecycleScope.launch {
                cfrmPaymentViewModel.updateStatus(paymentData?.id.toString(), StatusRequest("BANK_TRANSFER"))
                cfrmPaymentViewModel.paymentResp.observe(viewLifecycleOwner){
                    when(it.status){
                        Status.SUCCESS -> {
//                            val onBoarding = OnBoardingBottomSheet()
//                            onBoarding.show(childFragmentManager, onBoarding.tag)
                        }
                        Status.LOADING ->{}

                        Status.ERROR ->{}
                    }
                }
            }

        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the timer to prevent memory leaks
        timer.cancel()
    }


}