package com.c3.mobileapps.ui.course

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.ListCourseAdapter
import com.c3.mobileapps.databinding.FragmentCourseBinding
import com.c3.mobileapps.ui.payment.BottomSheetPayment
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private val courseViewModel: CourseViewModel by activityViewModel<CourseViewModel>()

    private lateinit var listCourseAdapter: ListCourseAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        getData()
        setupRvCourse()
        return binding.root
    }

    override fun onResume() {
        binding.etSearch.setText("")
        binding.cpAll.isChecked = true
        binding.cpKelasPremium.isChecked = false
        binding.cpKelasGratis.isChecked = false
        courseViewModel.dataFilter.value?.clear()
        getData()
        setupRvCourse()

        super.onResume()
    }

    private fun getData(){
        val dataCategory = arguments?.getString("CATEGORY")
        if (!dataCategory.isNullOrEmpty()){
            courseViewModel.addDataMapping("kategori",dataCategory)
        }

        courseViewModel.dataFilter.observe(viewLifecycleOwner) { dataFilter ->

            checkMode()

            val filterString = dataFilter["filter"]?.joinToString(",")
            val categoryString = dataFilter["kategori"]?.joinToString(",")
            val difficultyString = dataFilter["level"]?.joinToString(",")
            val typeString = dataFilter["type"]?.joinToString(",")

            Log.e("categoryFilter",dataFilter.toString())

            if(typeString.isNullOrEmpty()){
                binding.cpAll.isChecked = true
                binding.cpKelasPremium.isChecked = false
                binding.cpKelasGratis.isChecked = false
            }

            getCourse(
                type = typeString,
                filter = filterString,
                category = categoryString,
                difficulty = difficultyString,
                search = null
            )

            binding.filter.setOnClickListener {
                val fIlterBottomSheet = FIlterBottomSheet(dataFilter)
                fIlterBottomSheet.show(childFragmentManager, fIlterBottomSheet.tag)
            }

            binding.btnSearch.setOnClickListener {
                hideKeyboardAndClearFocus()
                getCourse(
                    type = typeString,
                    filter = filterString,
                    category = categoryString,
                    difficulty = difficultyString,
                    search = binding.etSearch.text.toString()
                )
            }
        }
    }

    private fun hideKeyboardAndClearFocus() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if the currently focused view is an EditText or has focusable property
        if (requireActivity().currentFocus is View) {
            val focusedView = requireActivity().currentFocus as View
            focusedView.clearFocus()
        }

        // Hide the keyboard
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun checkMode() {



            binding.cpAll.setOnClickListener {
                binding.cpAll.isChecked = true
                binding.cpKelasPremium.isChecked = false
                binding.cpKelasGratis.isChecked = false
                courseViewModel.addDataMapping("type", null)

            }
            binding.cpKelasPremium.setOnClickListener {
                binding.cpAll.isChecked = false
                binding.cpKelasGratis.isChecked = false
                courseViewModel.addDataMapping("type", "premium")
            }

            binding.cpKelasGratis.setOnClickListener {
                binding.cpKelasPremium.isChecked = false
                binding.cpAll.isChecked = false
                courseViewModel.addDataMapping("type", "free")
            }
    }

    private fun getCourse(
        type: String?,
        filter: String?,
        category: String?,
        search: String?,
        difficulty: String?
    ) {
        courseViewModel.getListCourse(type, filter, category, search, difficulty)
        courseViewModel.listCourse.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    listCourseAdapter.clearData()
                    Log.e("Cek Data Course", Gson().toJson(it.data))
                    showRecyclerView()
                    it.data?.let { listCourseAdapter.setData(it.data) }
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

    private fun showRecyclerView() {
        binding.shimmerFrameLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.rvCourse.visibility = View.VISIBLE
    }

    private fun setupRvCourse() {
        listCourseAdapter = ListCourseAdapter(emptyList(), onItemClick = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem, "CURRID" to R.id.courseFragment)
            findNavController().navigate(R.id.detailCourseFragment, bundle)
        },
            onBadgelick = { course ->
                val bottomSheetPayment = BottomSheetPayment(course,R.id.courseFragment)
                bottomSheetPayment.show(childFragmentManager, bottomSheetPayment.tag)
            })

        binding.rvCourse.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvCourse.adapter = listCourseAdapter
    }
}