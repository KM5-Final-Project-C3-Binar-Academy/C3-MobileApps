package com.c3.mobileapps.ui.detailCourse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentDetailCourseBinding
import org.koin.android.ext.android.inject

class DetailCourseFragment : Fragment() {

    private lateinit var binding: FragmentDetailCourseBinding
    private val detailCourseViewModel:DetailCourseViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDetailCourseBinding.inflate(inflater, container, false)

        return binding.root
    }
    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataDetail = arguments?.getParcelable<Course?>("pickItem")
        dataDetail?.let {
            binding.tvNamaKelas.text = dataDetail.name
            binding.deskripsiJudulKelas.text = dataDetail.courseCategory?.name
            binding.creatorKelas.text = dataDetail.author
            binding.levelNameKelas.text = dataDetail.difficulty
            binding.rating.text = dataDetail.rating.toString()
            binding.durasiKelas.text = "?"
            binding.jumlahModulKelas.text = "?"

            Glide.with(binding.root.context)
                .load(dataDetail.image)
                .into(binding.imageView2)
        }

        buttonUpBack()
    }

    private fun buttonUpBack() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }



}