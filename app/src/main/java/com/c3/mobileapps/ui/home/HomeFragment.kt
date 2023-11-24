package com.c3.mobileapps.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.c3.mobileapps.adapters.CategoryCourseAdapter
import com.c3.mobileapps.data.remote.model.response.course.CategoryCourse
import com.c3.mobileapps.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listCategory = mutableListOf<CategoryCourse>()

        listCategory.add(CategoryCourse("1","UI/UX Design","R.id.apa"))
        listCategory.add(CategoryCourse("2","Product Management","R.id.apa"))
        listCategory.add(CategoryCourse("3","Web Development","R.id.apa"))
        listCategory.add(CategoryCourse("4","Android Development","R.id.apa"))

        val adapter = CategoryCourseAdapter(listCategory)
        binding.rvCategoryCourse.adapter = adapter
        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireActivity(), 2)
    }

}