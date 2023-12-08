package com.c3.mobileapps.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.data.local.filter.FilterCategory
import com.c3.mobileapps.data.remote.model.response.course.Category
import com.c3.mobileapps.databinding.ItemCheckboxBinding
import com.c3.mobileapps.databinding.ItemHeaderBinding

class FilterAdapter(
    private val data: List<Any>,
    private val checkedItemsMap: MutableMap<String, MutableList<String>>,
    private val filterItemClickListener: ((FilterCategory, type: String) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_HEADER = 0
        private const val ITEM_FILTER = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is String -> ITEM_HEADER
            is FilterCategory -> ITEM_FILTER
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    class FilterViewHolder(private val binding: ItemCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            data: FilterCategory,
            filterItemClickListener: ((FilterCategory, type: String) -> Unit)? = null,
            checkedItemsMap: MutableMap<String, MutableList<String>>
        ) {
            binding.cbItem.text = data.viewName
            binding.cbItem.isChecked = data.isChecked

            val type = data.type
            val isChecked = checkedItemsMap[type]?.contains(data.name) == true
            binding.cbItem.isChecked = isChecked

            // Handle checkbox click

            binding.cbItem.setOnCheckedChangeListener { _, isChecked ->
                data.isChecked = isChecked
                Log.d("Filter ClickedA", "Name: ${data.name}, isChecked: ${data.isChecked}")
                filterItemClickListener?.invoke(data, data.type)
            }

        }
    }

    class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {
            binding.textHeader.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            ITEM_HEADER -> {
                val binding =
                    ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }

            ITEM_FILTER -> {
                val binding =
                    ItemCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FilterViewHolder(binding)
            }

            else -> throw throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_HEADER -> {
                val headerHolder = holder as HeaderViewHolder
                headerHolder.onBind(data[position] as String)
            }

            ITEM_FILTER -> {
                val itemHolder = holder as FilterViewHolder
                itemHolder.onBind(
                    data[position] as FilterCategory,
                    filterItemClickListener,
                    checkedItemsMap
                )
            }

            else -> throw IllegalArgumentException("Undefined view type")
        }


    }

    override fun getItemCount(): Int = data.size
}