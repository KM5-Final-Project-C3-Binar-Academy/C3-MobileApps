package com.c3.mobileapps.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.data.local.model.FilterCategory
import com.c3.mobileapps.databinding.ItemCheckboxBinding
import com.c3.mobileapps.databinding.ItemHeaderBinding
import com.c3.mobileapps.utils.DiffUtils

class FilterAdapter(
    private val checkedItemsMap: MutableMap<String, MutableList<String>>,
    private val filterItemClickListener: ((FilterCategory, type: String) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = emptyList<Any>()

    fun setData(newDaata: List<Any>) {
        val diffUtil = DiffUtils(data, newDaata)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = newDaata
        diffUtilResult.dispatchUpdatesTo(this)
    }

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