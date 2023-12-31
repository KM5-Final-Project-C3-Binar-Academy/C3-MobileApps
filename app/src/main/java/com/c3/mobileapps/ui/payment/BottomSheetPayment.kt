package com.c3.mobileapps.ui.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentBottomSheetPaymentBinding
import com.c3.mobileapps.ui.nonlogin.NonLoginBottomSheet
import com.c3.mobileapps.utils.NotificationHelper
import com.c3.mobileapps.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject


class BottomSheetPayment(private val dataCourse: Course,private val currentFragment: Int) : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBottomSheetPaymentBinding
    private val paymentViewModel: PaymentViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetPaymentBinding.inflate(inflater, container, false)
        return  binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNamaKelas.text = dataCourse.courseCategory?.name
        binding.deskripsiJudulKelas.text = dataCourse.name
        binding.creatorKelas.text = "by ${dataCourse.author}"
        binding.levelNameKelas.text = dataCourse.difficulty
        binding.rating.text = dataCourse.rating
        binding.durasiKelas.text = "${dataCourse.totalDuration.toString()} Menit"
        binding.jumlahModulKelas.text = "${dataCourse.totalMaterials.toString()} Modul"
        Glide.with(binding.root.context)
            .load(dataCourse.image ?: dataCourse.courseCategory?.image)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageView)

        paymentViewModel.isLogin.observe(viewLifecycleOwner){
            if (it){
                if (dataCourse.premium == true){
                    val price = "Beli Rp.${dataCourse.price}"
                    binding.btnPremium.text = price
                    binding.btnPremium.visibility = View.VISIBLE
                    binding.btnNextPayment.setOnClickListener {
                        enrollPremium(dataCourse)
                    }

                }else{
                    binding.tvKelas.text = "Kelas Free"
                    binding.btnPremium.visibility = View.GONE
                    binding.btnNextPayment.visibility= View.GONE
                    binding.btnMulaiBelajar.visibility = View.VISIBLE
                    binding.btnMulaiBelajar.setOnClickListener {
                        enrollFree(dataCourse)
                    }

                }
            }else{
                dismiss()
                val nonLoginBottomSheet = NonLoginBottomSheet(currentFragment)
                nonLoginBottomSheet.isCancelable = true
                nonLoginBottomSheet.show(childFragmentManager, nonLoginBottomSheet.tag)
            }
        }




        //if data.premium > beli sekarang
        //mulai belajar
    }

    private fun enrollFree(data: Course) {
        paymentViewModel.enrollFree(data.id.toString())
        paymentViewModel.paymentResp.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    //onboarding bottom sheet
                    val onBoardingBottomSheet = OnBoardingBottomSheet(data,currentFragment)
                    onBoardingBottomSheet.show(childFragmentManager, onBoardingBottomSheet.tag)
                    dismiss()


                    //tambah notif
                    NotificationHelper.makeStatusNotification(data.name.toString(), requireContext())
                    val bottomNavigationView: BottomNavigationView? =
                        activity?.findViewById(R.id.bottom_navigation)
                    val badge = bottomNavigationView?.getOrCreateBadge(R.id.notificationFragment)

                    badge?.number = badge?.number?.plus(1)!!
                }

                Status.LOADING -> {}

                Status.ERROR -> {
                    dismiss()
                    Snackbar.make(binding.root, "Anda Sudah Bergabung Dengan Kelas", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                    val bundle = bundleOf("pickItem" to data)
                    findNavController().navigate(R.id.detailCourseFragment, bundle)
                }

            }
        }
    }

    private fun enrollPremium(data: Course) {
        paymentViewModel.createPayment(data.id.toString())
        paymentViewModel.paymentResp.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    dismiss()
                    val bundle = bundleOf("COURSE" to data,"PAYMENT" to it.data?.data, "CURRID" to currentFragment)

                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(currentFragment, true)
                        .build()

                    findNavController().navigate(R.id.paymentFragment, bundle, navOptions)
                }

                Status.LOADING -> {}

                Status.ERROR -> {
                    dismiss()
                    Snackbar.make(binding.root, "Anda Sudah Bergabung Dengan Kelas", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                    val bundle = bundleOf("pickItem" to data)
                    findNavController().navigate(R.id.detailCourseFragment, bundle)
                }

            }
        }
    }


}