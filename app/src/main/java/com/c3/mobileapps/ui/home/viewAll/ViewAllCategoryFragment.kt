package com.c3.mobileapps.ui.home.viewAll

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.AllCategoryCourseAdapter
import com.c3.mobileapps.databinding.FragmentViewAllCategoryBinding
import com.c3.mobileapps.ui.home.HomeViewModel
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class ViewAllCategoryFragment : Fragment() {

    private lateinit var binding: FragmentViewAllCategoryBinding
    private val homeViewModel: HomeViewModel by inject()
    private lateinit var categoryCourseAdapter: AllCategoryCourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewAllCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDataCategory()
        setupRvCategory()
        buttonUpBack()
    }

    private fun loadDataCategory() {

        lifecycleScope.launch {
            homeViewModel.readCategory.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("data category", "list category view from database")
                    categoryCourseAdapter.setData(database.first().categoryResponse.data)
                } else {
                    getCategory()
                }
            }
        }
    }

    private fun getCategory(){
        homeViewModel.getListCategory()
        homeViewModel.listCategory.observe(viewLifecycleOwner) {it ->
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Category", Gson().toJson(it.data))
                    it.data?.let { categoryCourseAdapter.setData(it.data) }
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

    private fun setupRvCategory(){
        categoryCourseAdapter = AllCategoryCourseAdapter(emptyList())
        binding.rvViewAll.setHasFixedSize(true)
        binding.rvViewAll.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvViewAll.adapter = categoryCourseAdapter
    }

    private fun buttonUpBack() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}