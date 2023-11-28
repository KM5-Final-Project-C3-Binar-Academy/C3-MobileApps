package com.c3.mobileapps.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.CategoryCourseAdapter
import com.c3.mobileapps.adapters.PopulerCourseAdapter
import com.c3.mobileapps.data.remote.model.response.course.Category
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentHomeBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getAllCategory().observe(viewLifecycleOwner){
            when (it.status){
                Status.SUCCESS -> {
                    Log.e("Cek Data", Gson().toJson(it.data))
                    val response = it.data?.data

                    val listCategory: List<Category> = response.orEmpty().filterNotNull()
                    val adapter = CategoryCourseAdapter(listCategory)
                    binding.rvCategoryCourse.adapter = adapter
                    binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireActivity(), 2)

                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        }

        homeViewModel.getAllCourse().observe(viewLifecycleOwner){
            when (it.status){
                Status.SUCCESS -> {
                    Log.e("Cek Data", Gson().toJson(it.data))
                    val response = it.data?.data

                    val listCourse: List<Course> = response.orEmpty().filterNotNull()
                    val adapter = PopulerCourseAdapter(listCourse)
                    binding.rvPopulerCourse.adapter = adapter
                    binding.rvPopulerCourse.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        }



    }

}