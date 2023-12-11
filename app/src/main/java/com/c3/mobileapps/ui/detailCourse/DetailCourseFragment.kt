package com.c3.mobileapps.ui.detailCourse

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.c3.mobileapps.adapters.PagerAdapter
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentDetailCourseBinding
import com.c3.mobileapps.utils.Status
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
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
            Glide.with(binding.root.context)
                .load(dataDetail.image)
                .into(binding.imageView2)

            val idCourse = dataDetail.id
            getCourseDetail(idCourse)}

        val idcourse = dataDetail?.id

        val fragment = arrayListOf(DetailTentangFragment.newInstance(idcourse),
            DetailMateriFragment.newInstance(idcourse))

        val titleFragment = arrayListOf("Tentang", "Materi Kelas")

        val viewPager2AdapterAdapter = PagerAdapter(requireActivity(), fragment)

        binding.viewPager.adapter = viewPager2AdapterAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleFragment[position]
        }.attach()


        buttonUpBack()
    }

    @SuppressLint("SetTextI18n")
    private fun getCourseDetail(id: String?){
        detailCourseViewModel.getCourseById(id)
        detailCourseViewModel.courseById.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    Log.e("Cek Data Course", Gson().toJson(it.data))
                    binding.progressBar.isVisible = false

                    binding.tvNamaKelas.text = it.data?.data?.course_category?.name
                    binding.deskripsiJudulKelas.text = it.data?.data?.name
                    binding.creatorKelas.text = it.data?.data?.author
                    binding.levelNameKelas.text =  it.data?.data?.difficulty
                    binding.rating.text =  it.data?.data?.rating.toString()
                    binding.durasiKelas.text = "${it.data?.data?.total_duration.toString()} Menit"
                    binding.jumlahModulKelas.text =  "${it.data?.data?.total_materials.toString()} Modul "

//                    it.data?.data?.course_chapter?.forEach{
//                        it.
//                    }
                }
                Status.ERROR -> {
                    Log.e("Cek Data Course", it.message.toString())
                    binding.progressBar.isVisible = false
                }

                Status.LOADING -> {
                    binding.progressBar.isVisible = true

                }
            }
        }
    }
    private fun buttonUpBack() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}