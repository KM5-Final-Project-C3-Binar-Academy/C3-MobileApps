package com.c3.mobileapps.ui.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentBottomSheetPaymentBinding
import com.c3.mobileapps.ui.confirm_payment.OnBoardingBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetPayment(private val dataCourse: Course) : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBottomSheetPaymentBinding


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

        if (dataCourse.premium == true){
            val price = "Beli Rp.${dataCourse.price}"
            binding.btnPremium.text = price
            binding.btnPremium.visibility = View.VISIBLE
            binding.btnNextPayment.setOnClickListener {
                val bundle = bundleOf("COURSE_ID" to dataCourse.id.toString())
                findNavController().navigate(R.id.paymentFragment, bundle)
            }

        }else{
            binding.tvKelas.text = "Kelas Free"
            binding.btnPremium.visibility = View.GONE
            binding.btnNextPayment.visibility= View.GONE
            binding.btnMulaiBelajar.visibility = View.VISIBLE
            binding.btnMulaiBelajar.setOnClickListener {
                val onBoardingBottomSheet = OnBoardingBottomSheet()
                onBoardingBottomSheet.show(childFragmentManager, onBoardingBottomSheet.tag)
            }

        }


        //if data.premium > beli sekarang
        //mulai belajar
    }


}