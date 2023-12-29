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
import com.c3.mobileapps.databinding.FragmentHomeBinding
import com.c3.mobileapps.ui.payment.BottomSheetPayment
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by inject()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryFilterAdapter: CategoryFilterAdapter
    private lateinit var listCourseAdapter: ListCourseAdapter

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
        getCategory2()
        populerByCategory("All")

        binding.lihatSemuaKategori.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("ModeView", true)
            findNavController().navigate(R.id.viewAllFragment, bundle)
        }

        binding.expandKursusPopuler.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("ModeView", false)

            findNavController().navigate(R.id.viewAllFragment, bundle)
        }

        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Do something when the EditText is focused
                findNavController().navigate(R.id.searchFragment)
            }

        }

    }

    private fun setupRecyclerView() {

        categoryFilterAdapter = CategoryFilterAdapter {
            populerByCategory(it)
        }
        categoryAdapter = CategoryAdapter(
            isAll = false,
            listener = { category ->
                val bundle = bundleOf("CATEGORY" to category)
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.homeFragment, true)
                    .build()
                findNavController().navigate(R.id.courseFragment, bundle, navOptions)
            })
        listCourseAdapter = ListCourseAdapter(emptyList(), onItemClick = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)

            findNavController().navigate(R.id.detailCourseFragment, bundle)
        },
            onBadgelick = { course ->
                val bottomSheetPayment = BottomSheetPayment(course, R.id.homeFragment)
                bottomSheetPayment.show(childFragmentManager, bottomSheetPayment.tag)
            })

        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCategoryCourse.adapter = categoryAdapter

        binding.rvFilter.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFilter.adapter = categoryFilterAdapter

        binding.rvKelas.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvKelas.adapter = listCourseAdapter

    }

   private fun loadDataCategory() {
        lifecycleScope.launch {
            homeViewModel.lisCategoryLocal.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("data category", "list category view from database")
                        categoryAdapter.setData(database)
                        categoryFilterAdapter.setData(database)
                        showRvCategory()
                }
            }
        }
    }


    private fun getCategory2() {
        homeViewModel.getListCategory2()
        homeViewModel.listCategory2.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {

                }

                Status.ERROR -> {
                    Log.e("Data Category2", it.message.toString())

                }

                Status.LOADING -> {

                }
            }

        }
    }

    private fun populerByCategory(cat: String) {
        homeViewModel.getListCourse(cat)
        homeViewModel.listCourse.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Category", Gson().toJson(it.data))

                    it.data?.let {
                        showRvCourse()
                        listCourseAdapter.setData(it.data)

                    }
                }

                Status.ERROR -> {
                    binding.shimmerFrameLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                }

                Status.LOADING -> {
                    binding.shimmerFrameLayout.startShimmer()

                }
            }

        }
    }

    private fun showRvCourse() {
        binding.shimmerFrameLayout.apply {
            stopShimmer()
            visibility = View.INVISIBLE
        }
        binding.rvKelas.visibility = View.VISIBLE
    }

    private fun showRvCategory() {
        binding.shimmerCategory.apply {
            stopShimmer()
            visibility = View.INVISIBLE
        }
        binding.rvCategoryCourse.visibility = View.VISIBLE
    }

}