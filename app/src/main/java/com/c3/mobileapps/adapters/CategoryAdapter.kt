package com.c3.mobileapps.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c3.mobileapps.data.local.model.CategoryLocal
import com.c3.mobileapps.databinding.ItemCategoryCourseBinding
import com.c3.mobileapps.utils.DiffUtils

class CategoryAdapter(
    private var listener: ((String) -> Unit)? = null,
    private var isAll: Boolean
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var listCategory = emptyList<CategoryLocal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listCategory[position]
        holder.onBind(item)

        holder.itemView.setOnClickListener {
            listener?.invoke(item.name!!)
        }
    }

    override fun getItemCount(): Int {
        return if (isAll) listCategory.size else listCategory.size.minus(2)


    }

    fun setData(newCategory: List<CategoryLocal>) {
        val diffUtil = DiffUtils(listCategory, newCategory)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        listCategory = newCategory
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ItemCategoryCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: CategoryLocal) {
            binding.tvNameCategory.text = data.name
            Glide.with(binding.root.context)
                .load(data.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivImageCategory)
        }
    }
}