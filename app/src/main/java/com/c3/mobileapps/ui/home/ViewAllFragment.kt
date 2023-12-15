package com.c3.mobileapps.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.CategoryAdapter
import com.c3.mobileapps.adapters.CategoryFilterAdapter
import com.c3.mobileapps.adapters.ListCourseAdapter
import com.c3.mobileapps.databinding.FragmentViewAllBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ViewAllFragment : Fragment() {

    private lateinit var binding: FragmentViewAllBinding

    private val homeViewModel: HomeViewModel by inject()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryFilterAdapter: CategoryFilterAdapter
    private lateinit var listCourseAdapter: ListCourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewAllBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //check bundle bawa data iscategory/ iskelas favorit
        val isCategory = arguments?.getBoolean("ModeView")
        setupRecyclerView(isCategory!!)
        loadDataCategory()
        populerByCategory("All")

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupRecyclerView(isCategory: Boolean) {

        categoryFilterAdapter = CategoryFilterAdapter{
            populerByCategory(it)
        }
        categoryAdapter = CategoryAdapter(
            isAll = true,
            listener = {category ->
                val bundle = bundleOf("CATEGORY" to category)
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.homeFragment, true)
                    .build()
                findNavController().navigate(R.id.courseFragment,bundle,navOptions)
            })
        listCourseAdapter = ListCourseAdapter(emptyList(), listener = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)
            findNavController().navigate(R.id.detailCourseFragment, bundle)
        })

        if (isCategory){
            binding.rvFilterAll.visibility = View.GONE
            binding.rvViewAll.layoutManager = GridLayoutManager(requireActivity(), 2)
            binding.rvViewAll.adapter = categoryAdapter
        }else{
            binding.rvFilterAll.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvFilterAll.adapter = categoryFilterAdapter

            binding.rvViewAll.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            binding.rvViewAll.adapter = listCourseAdapter
        }

    }


    private fun loadDataCategory() {
        lifecycleScope.launch {
            homeViewModel.readCategory.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("data category", "list category view from database")
                    categoryAdapter.setData(database.first().categoryResponse.data)
                    categoryFilterAdapter.setData(database.first().categoryResponse.data)
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
                        categoryAdapter.setData(it.data)
                        categoryFilterAdapter.setData(it.data)

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
                        listCourseAdapter.setData(it.data)

                    }
                }

                Status.ERROR -> {
                    Log.e("Cek Data Category", it.message.toString())
                }

                Status.LOADING -> {

                }
            }

        }
    }

}