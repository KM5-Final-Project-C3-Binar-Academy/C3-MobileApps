package com.c3.mobileapps.ui.course

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.ListCourseAdapter
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentCourseBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private val courseViewModel: CourseViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        courseViewModel.getAllCourse().observe(viewLifecycleOwner){
            when (it.status){
                Status.SUCCESS -> {
                    Log.e("Cek Data", Gson().toJson(it.data))
                    binding.progressBarMenu.isVisible = false
                    val response = it.data?.data
                    val data: List<Course> = response.orEmpty().filterNotNull()
                    val adapter = ListCourseAdapter(data)
                    binding.rvCourse.adapter = adapter
                    binding.rvCourse.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )

                }
                Status.ERROR -> {
                    Log.e("Cek Data", it.message.toString())
                    binding.progressBarMenu.isVisible = false

                }
                Status.LOADING -> {
                    binding.progressBarMenu.isVisible = true
                }
            }
        }

    }

}