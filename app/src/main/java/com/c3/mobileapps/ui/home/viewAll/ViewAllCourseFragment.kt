package com.c3.mobileapps.ui.home.viewAll

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.CategoryCourseAdapter
import com.c3.mobileapps.adapters.PopulerCourseAdapter
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentViewAllCourseBinding
import com.c3.mobileapps.ui.home.HomeViewModel
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class ViewAllCourseFragment : Fragment() {
	private lateinit var binding: FragmentViewAllCourseBinding
	private val homeViewModel: HomeViewModel by inject()
	private lateinit var listCourse: List<Course>

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		binding = FragmentViewAllCourseBinding.inflate(inflater,container,false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		getCourse()
		buttonUpBack()

	}

	private fun getCourse(){
		homeViewModel.getAllCourse().observe(viewLifecycleOwner) {
			when (it.status) {
				Status.SUCCESS -> {
					Log.e("Cek Data", Gson().toJson(it.data))
					val response = it.data?.data

					listCourse = response.orEmpty()
					val adapter = PopulerCourseAdapter(listCourse)
					binding.rvViewAll.adapter = adapter
					binding.rvViewAll.layoutManager = LinearLayoutManager(
						requireActivity(),
						LinearLayoutManager.VERTICAL,
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

	private fun buttonUpBack() {
		binding.back.setOnClickListener {
			requireActivity().onBackPressed()
		}
	}
}