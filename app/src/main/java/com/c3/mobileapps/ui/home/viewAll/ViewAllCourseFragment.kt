package com.c3.mobileapps.ui.home.viewAll

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.AllCategoryCourseAdapter
import com.c3.mobileapps.adapters.CategoryCourseAdapter
import com.c3.mobileapps.data.remote.model.response.course.CategoryCourse
import com.c3.mobileapps.databinding.FragmentViewAllCourseBinding

class ViewAllCourseFragment : Fragment() {
	private lateinit var binding: FragmentViewAllCourseBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentViewAllCourseBinding.inflate(inflater,container,false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val listCategory = mutableListOf<CategoryCourse>()

		// Existing data
		listCategory.add(CategoryCourse("1", "UI/UX Design", "R.id.apa"))
		listCategory.add(CategoryCourse("2", "Product Management","R.id.apa"))
		listCategory.add(CategoryCourse("3", "Web Development", "R.id.apa"))
		listCategory.add(CategoryCourse("4", "Android Development", "R.id.apa"))
		listCategory.add(CategoryCourse("5", "AI Development", "R.id.apa"))
		listCategory.add(CategoryCourse("6", "Product Management", "R.id.apa"))
		listCategory.add(CategoryCourse("7", "Business Intelligent", "R.id.apa"))
		listCategory.add(CategoryCourse("8", "Fullstack Development", "R.id.apa"))
		listCategory.add(CategoryCourse("9", "Data Science", "R.id.apa"))
		listCategory.add(CategoryCourse("10", "Cybersecurity", "R.id.apa"))
		listCategory.add(CategoryCourse("11", "Mobile App Development","R.id.apa"))
		listCategory.add(CategoryCourse("12", "Game Development","R.id.apa"))
		listCategory.add(CategoryCourse("13", "Cloud Computing", "R.id.apa"))
		listCategory.add(CategoryCourse("14", "Machine Learning","R.id.apa"))
		listCategory.add(CategoryCourse("15", "DevOps", "R.id.apa"))
		listCategory.add(CategoryCourse("16", "Blockchain", "R.id.apa"))

		val adapter = AllCategoryCourseAdapter(listCategory)
		binding.rvViewAll.adapter = adapter
		binding.rvViewAll.layoutManager = GridLayoutManager(requireActivity(), 2)
	}
}