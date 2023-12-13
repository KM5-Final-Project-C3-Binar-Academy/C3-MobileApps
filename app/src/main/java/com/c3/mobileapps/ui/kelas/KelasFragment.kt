package com.c3.mobileapps.ui.kelas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.CategoryCourseAdapter
import com.c3.mobileapps.databinding.FragmentKelasBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class KelasFragment : Fragment() {

    private lateinit var binding: FragmentKelasBinding
    private val kelasViewModel: KelasViewModel by inject()
    private lateinit var categoryCourseAdapter: CategoryCourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentKelasBinding.inflate(inflater, container, false)

        setupRecyclerView()
        loadDataCategory()

        binding.lihatSemuaKategori.setOnClickListener {
            findNavController().navigate(R.id.action_kelasFragment_to_viewAllCategoryFragment)
        }


        return binding.root
    }

    private fun loadDataCategory() {
        lifecycleScope.launch {
            kelasViewModel.readCategory.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("data category", "list category view from database")
                    categoryCourseAdapter.setData(database.first().categoryResponse.data)
                } else {
                    getCategory()
                }
            }
        }
    }

    private fun getCategory() {

        kelasViewModel.getListCategory()
        kelasViewModel.listCategory.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Category", Gson().toJson(it.data))
                    it.data?.let {
                        categoryCourseAdapter.setData(it.data)
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

    private fun setupRecyclerView() {
        categoryCourseAdapter = CategoryCourseAdapter(listener = null)
        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCategoryCourse.adapter = categoryCourseAdapter

    }


}