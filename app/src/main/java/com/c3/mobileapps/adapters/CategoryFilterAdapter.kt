package com.c3.mobileapps.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.data.local.model.CategoryLocal
import com.c3.mobileapps.databinding.ItemFilterBinding
import com.c3.mobileapps.utils.DiffUtils

class CategoryFilterAdapter(private var listener: ((String) -> Unit)? = null)  : RecyclerView.Adapter<CategoryFilterAdapter.ViewHolder>()  {

	private var categoryItem = emptyList<CategoryLocal>()
	private var selectedCategory: String? = "All"
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding =
			ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return categoryItem.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = categoryItem[position]
		holder.onBind(item,getSelectedCategory())

		holder.itemView.setOnClickListener {
			handleSelection(item)
		}

	}

	fun setData(newCategory: List<CategoryLocal>) {
		val allCategory = CategoryLocal(itemId = null, id = null, name = "All", image = null)
		val updatedList = mutableListOf(allCategory)
		updatedList.addAll(newCategory)

		val diffUtil = DiffUtils(categoryItem, updatedList)
		val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
		categoryItem = updatedList
		diffUtilResult.dispatchUpdatesTo(this)
	}

	private fun handleSelection(item: CategoryLocal) {
		if (selectedCategory == item.name) {
			// Already selected, do nothing
			selectedCategory = item.name
			notifyDataSetChanged() // Update UI to reflect the selection

			listener?.invoke(selectedCategory!!)
		}else {

			selectedCategory = item.name
			notifyDataSetChanged() // Update UI to reflect the selection

			listener?.invoke(selectedCategory!!)
		}
	}

	fun getSelectedCategory(): String? {
		return selectedCategory
	}

	class ViewHolder (private val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root)  {
		fun onBind(data: CategoryLocal, selectedCategory: String?){
			binding.chipFilterCategory.text = data.name

			Log.d("cek selected",(data.name == selectedCategory).toString())
			binding.chipFilterCategory.isChecked = (data.name == selectedCategory)

		}
	}
}