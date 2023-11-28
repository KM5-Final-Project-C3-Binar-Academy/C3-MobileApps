package com.c3.mobileapps.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.data.filterCategory
import com.c3.mobileapps.data.remote.model.response.course.Category
import com.c3.mobileapps.databinding.CategoryCourseItemBinding
import com.c3.mobileapps.databinding.FilterBottomSheetBinding
import com.c3.mobileapps.databinding.ItemCheckboxBinding

class ItemFilterAdapter(private val listItem: List<filterCategory>)  : RecyclerView.Adapter<ItemFilterAdapter.ViewHolder>()  {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding =
			ItemCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ItemFilterAdapter.ViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return listItem.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = listItem[position]
		holder.onBind(item)
	}

	class ViewHolder (private val binding: ItemCheckboxBinding) : RecyclerView.ViewHolder(binding.root)  {
		fun onBind(data: filterCategory){
			binding.cbItem.text = data.name
		}
	}
}