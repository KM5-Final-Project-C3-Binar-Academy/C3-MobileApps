package com.c3.mobileapps.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.PopulerCourseAdapter
import com.c3.mobileapps.databinding.FragmentCourseBinding

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding

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

//        binding.rvCourse.setHasFixedSize(true)
//        binding.rvCourse.adapter = PopulerCourseAdapter()
//        binding.rvCourse.layoutManager= LinearLayoutManager(requireActivity())
//

    }

}