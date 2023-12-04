package com.c3.mobileapps.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.ItemFilterAdapter
import com.c3.mobileapps.data.filterCategory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet : BottomSheetDialogFragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.filter_bottom_sheet, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val listItem = mutableListOf<filterCategory>()
		listItem.add(filterCategory("UI/UX Design"))
		listItem.add(filterCategory("Product Management"))
		listItem.add(filterCategory("Web Development"))
		listItem.add(filterCategory("Android Development"))
		listItem.add(filterCategory("AI Development"))
		listItem.add(filterCategory("Business Intelligent"))
		listItem.add(filterCategory("Fullstack Development"))
		listItem.add(filterCategory("Data Science"))
		listItem.add(filterCategory("Cyber security"))
		listItem.add(filterCategory("Mobile App Development"))
		listItem.add(filterCategory("Game Development"))
		listItem.add(filterCategory("Cloud Computing"))
		listItem.add(filterCategory("Machine Learning"))
		listItem.add(filterCategory("DevOps"))
		listItem.add(filterCategory("Blockchain"))

		val adapter = ItemFilterAdapter(listItem)

		// Inisialisasi rvFilter menggunakan findViewById
		val rvFilter = view.findViewById<RecyclerView>(R.id.rvFilter)
		val rvFilter2 = view.findViewById<RecyclerView>(R.id.rvFilter2)

		rvFilter.adapter = adapter
		rvFilter.layoutManager = LinearLayoutManager(requireActivity())

		rvFilter2.adapter = adapter
		rvFilter2.layoutManager = LinearLayoutManager(requireActivity())
	}
}
