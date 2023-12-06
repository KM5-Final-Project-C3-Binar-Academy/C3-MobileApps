package com.c3.mobileapps.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.CategoryCourseAdapter
import com.c3.mobileapps.adapters.ItemFilterAdapter
import com.c3.mobileapps.adapters.PopulerCourseAdapter
import com.c3.mobileapps.data.remote.model.response.course.Category
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentHomeBinding
import com.c3.mobileapps.utils.Status
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by inject()

    private lateinit var categoryCourseAdapter: CategoryCourseAdapter
    private lateinit var itemFilterAdapter: ItemFilterAdapter
    private lateinit var populerCourseAdapter: PopulerCourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadDataCategory()
        populerByCategory("All")

        binding.lihatSemuaKategori.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllCategoryFragment)
        }

        binding.expandKursusPopuler.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllCourseFragment)
        }

    }

    private fun setupRecyclerView() {

        itemFilterAdapter = ItemFilterAdapter{
                populerByCategory(it)
        }
        categoryCourseAdapter = CategoryCourseAdapter(listener = null)
        populerCourseAdapter = PopulerCourseAdapter(listener = null)

        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCategoryCourse.adapter = categoryCourseAdapter

        binding.rvFilter.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFilter.adapter = itemFilterAdapter

        binding.rvKelas.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvKelas.adapter = populerCourseAdapter

    }

    private fun loadDataCategory() {
        lifecycleScope.launch {
            homeViewModel.readCategory.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("data category", "list category view from database")
                    categoryCourseAdapter.setData(database.first().categoryResponse.data)
                    itemFilterAdapter.setData(database.first().categoryResponse.data)
                } else {
                    getCategory()
                }
            }
        }
    }

    private fun getCategory() {

        homeViewModel.getListCategory()
        homeViewModel.listCategory.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Category", Gson().toJson(it.data))
                    it.data?.let {
                        categoryCourseAdapter.setData(it.data)
                        itemFilterAdapter.setData(it.data)

                    }
                }

                Status.ERROR -> {
                    Log.e("Cek Data Category", it.message.toString())
                    loadDataCategory()
                }

                Status.LOADING -> {

                }
            }

        }
    }

    private fun populerByCategory(cat: String){
        homeViewModel.getListCourse(cat)
        homeViewModel.listCourse.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Category", Gson().toJson(it.data))
                    it.data?.let {
                        populerCourseAdapter.setData(it.data)

                    }
                }

                Status.ERROR -> {
                    Log.e("Cek Data Category", it.message.toString())
                    loadDataCategory()
                }

                Status.LOADING -> {

                }
            }

        }
    }

}