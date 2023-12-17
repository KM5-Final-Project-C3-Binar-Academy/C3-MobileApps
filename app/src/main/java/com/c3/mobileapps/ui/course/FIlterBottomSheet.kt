package com.c3.mobileapps.ui.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.FilterAdapter
import com.c3.mobileapps.data.local.filter.FilterCategory
import com.c3.mobileapps.databinding.FilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FIlterBottomSheet(private val oldCheckedItemsMap: MutableMap<String, MutableList<String>>) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FilterBottomSheetBinding
    private val courseViewModel: CourseViewModel by activityViewModel<CourseViewModel>()
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var dataFilter: MutableList<Any>
    private val checkedItemsMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterBottomSheetBinding.inflate(inflater, container, false)
        // Update checkedItemsMap with old

        setRecycleVIew()
        setDataFilter()
        checkedItemsMap.clear()
        checkedItemsMap.putAll(oldCheckedItemsMap)



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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun setRecycleVIew() {
        filterAdapter = FilterAdapter(
            filterItemClickListener = { data, type ->
                handleItemFilter(data, type)

            }, checkedItemsMap = checkedItemsMap
        )

        binding.rvFilter.adapter = filterAdapter

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
        lifecycleScope.launch {
            dataFilter = mutableListOf()

            dataFilter.add("Filter")
            dataFilter.add(FilterCategory("new", "Paling Baru", "filter"))
            dataFilter.add(FilterCategory("populer", "Paling Populer", "filter"))
            dataFilter.add(FilterCategory("promo", "Promo", "filter"))
            dataFilter.add("Kategori")
//        Add data category to list

            courseViewModel.readCategory.observe(viewLifecycleOwner) { data ->

                val listCategory = data.first().categoryResponse.data

                listCategory.forEach {
                    dataFilter.add(
                        FilterCategory(
                            it.name.toString(),
                            it.name.toString(),
                            "kategori"
                        )
                    )
                }

                //Add Level
                dataFilter.add("Level")
                dataFilter.add(FilterCategory("beginner", "Beginner Level", "level"))
                dataFilter.add(FilterCategory("intermediate", "Intermediate Level", "level"))
                dataFilter.add(FilterCategory("advanced", "Advanced Level", "level"))

//                binding.rvFilter.adapter?.notifyDataSetChanged()

                filterAdapter.setData(dataFilter)

            }
        }


    }
}
