package com.c3.mobileapps.ui.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.FilterAdapter
import com.c3.mobileapps.data.local.filter.FilterCategory
import com.c3.mobileapps.data.remote.model.response.course.Category
import com.c3.mobileapps.databinding.FilterBottomSheetBinding
import com.c3.mobileapps.databinding.FragmentCourseBinding
import com.c3.mobileapps.ui.course.CourseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FIlterBottomSheet(private val oldCheckedItemsMap: MutableMap<String, MutableList<String>>) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FilterBottomSheetBinding
    private val courseViewModel: CourseViewModel by activityViewModel<CourseViewModel>()

    private lateinit var dataFilter: MutableList<Any>
    private val checkedItemsMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update checkedItemsMap with old data
        checkedItemsMap.clear()
        checkedItemsMap.putAll(oldCheckedItemsMap)

        setDataFilter()
        setRecycleVIew()

        binding.btnTerapkanFilter.setOnClickListener {
            courseViewModel.setIsFiltered(checkedItemsMap)

            Log.d("filter", "clicked")

            dismiss()
        }

        binding.btnHapusFilter.setOnClickListener {
            checkedItemsMap.clear()
            courseViewModel.setIsFiltered(checkedItemsMap)
            dismiss()


        }
    }

    private fun setRecycleVIew() {
        binding.rvFilter.adapter = FilterAdapter(dataFilter,
            filterItemClickListener = { data, type ->
                handleItemFilter(data, type)

            }, checkedItemsMap = checkedItemsMap
        )

        binding.rvFilter.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun handleItemFilter(data: FilterCategory, type: String) {
        if (data.isChecked) {
            if (!checkedItemsMap.containsKey(type)) {
                checkedItemsMap[type] = mutableListOf()
            }
            checkedItemsMap[type]?.add(data.name)
        } else {
            // Remove unchecked items from the map
            checkedItemsMap[type]?.remove(data.name)
        }

    }

    private fun setDataFilter() {

        dataFilter = mutableListOf(
            "Filter",
            FilterCategory("new","Paling Baru", "filter"),
            FilterCategory("populer","Paling Populer", "filter"),
            FilterCategory("promo","Promo", "filter"),
            "Kategori"
        )
//        Add data category to list

        courseViewModel.readCategory.observe(viewLifecycleOwner) { data ->

            val listCategory = data.first().categoryResponse.data

            listCategory.forEach {
                Log.d("cek filter", it.name.toString())
                dataFilter.add(FilterCategory(it.name.toString(),it.name.toString(), "kategori"))
            }

            //Add Level
            dataFilter.add("Level")
            dataFilter.add(FilterCategory("beginner","Beginner Level", "level"))
            dataFilter.add(FilterCategory("intermediate","Intermediate Level", "level"))
            dataFilter.add(FilterCategory("advanced","Advanced Level", "level"))

            binding.rvFilter.adapter?.notifyDataSetChanged()

        }
    }
}
