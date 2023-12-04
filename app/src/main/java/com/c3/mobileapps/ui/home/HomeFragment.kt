package com.c3.mobileapps.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.CategoryCourseAdapter
import com.c3.mobileapps.adapters.PopulerCourseAdapter
import com.c3.mobileapps.data.remote.model.response.course.Category
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentHomeBinding
import com.c3.mobileapps.utils.Status
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by inject()
    private lateinit var listCourse: List<Course>
//    private lateinit var categoryCourseAdapter: CategoryCourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getCourse()
        getCategory()
//        loadDataCategory()
//        setupRvCategory()

        binding.lihatSemuaKategori.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllCategoryFragment)
        }

        binding.expandKursusPopuler.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllCourseFragment)
        }

    }
    private fun getCourse(){
        homeViewModel.getAllCourse().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data", Gson().toJson(it.data))
                    val response = it.data?.data

                    listCourse = response.orEmpty()
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

//    private fun loadDataCategory() {
//
//        lifecycleScope.launch {
//            homeViewModel.readCategory.observe(viewLifecycleOwner) { database ->
//                if (database.isNotEmpty()) {
//                    Log.d("data category", "list category view from database")
//                    categoryCourseAdapter.setData(database.first().categoryResponse.data)
//                } else {
//                    getCategory()
//                }
//            }
//        }
//    }

    private fun getCategory(){
        homeViewModel.getListCategory()
        homeViewModel.listCategory.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Category", Gson().toJson(it.data))
//                    it.data?.let { categoryCourseAdapter.setData(it.data) }

                    val response = it.data?.data
                    val listCategory: List<Category> = response.orEmpty()
                    val adapter = CategoryCourseAdapter(listCategory)
                    binding.rvCategoryCourse.adapter = adapter
                    binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireActivity(), 2)


                    binding.categoryChipGroup.addChip(requireContext(), "All")
                    for (category in listCategory) {
                        binding.categoryChipGroup.addChip(requireContext(), "${category.name}")
                    }

                    // Set up OnClickListener for each chip to prevent deselection on double-click
                    for (i in 0 until binding.categoryChipGroup.childCount) {
                        if (i == 0) {
                            val allChip = binding.categoryChipGroup.getChildAt(0) as? Chip
                            allChip?.isChecked = true
                        }
                        val chip = binding.categoryChipGroup.getChildAt(i) as? Chip
                        chip?.setOnClickListener { handleChipClick(chip) }
                    }
                }

                Status.ERROR -> {
                    Log.e("Cek Data Category", it.message.toString())
//                    loadDataCategory()
                }

                Status.LOADING -> {

                }
            }
        }
    }



//    private fun setupRvCategory(){
//        categoryCourseAdapter = CategoryCourseAdapter(emptyList())
//        binding.rvCategoryCourse.setHasFixedSize(true)
//        binding.rvCategoryCourse.layoutManager = GridLayoutManager(context, 2)
//        binding.rvCategoryCourse.adapter = categoryCourseAdapter
//    }


    private fun handleChipClick(clickedChip: Chip?) {
        for (i in 0 until binding.categoryChipGroup.childCount) {
            val chip = binding.categoryChipGroup.getChildAt(i) as? Chip

            if (chip == clickedChip) {
                chip?.isChecked = true
                Toast.makeText(requireContext(), "Selected: ${chip?.text}", Toast.LENGTH_SHORT).show()
                // Perform actions related to the selected chip (if needed)
                // ...
                var filteredList: List<Course>
                listCourse.forEach {
                    val categoryName = it.courseCategory?.name ?: "All"
                    if (categoryName == chip?.text){
                        filteredList = listCourse.filter { item ->
                            item.courseCategoryId == it.courseCategoryId
                        }
                        Log.e("Cek Item Perkagegori",filteredList.size.toString())

                    }
                }

            } else {
                chip?.isChecked = false
            }
        }

    }

    private fun ChipGroup.addChip(context: Context, label: String) {
        Chip(context).apply {
            id = View.generateViewId()
            text = label
            isClickable = true
            isCheckable = true
            isFocusable = true
            addView(this)
        }

    }

}