package com.c3.mobileapps.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c3.mobileapps.data.remote.model.response.course.Category
import com.c3.mobileapps.databinding.CategoryCourseItemBinding
import com.c3.mobileapps.utils.CourseDiffUtil

class CategoryCourseAdapter( private var listener: (() -> Unit)? = null): RecyclerView.Adapter<CategoryCourseAdapter.ViewHolder>() {

    private var listCategory = emptyList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoryCourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listCategory[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = listCategory.size.minus(2)

    fun setData(newCategory: List<Category>) {

        val diffUtil = CourseDiffUtil(listCategory, newCategory)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        listCategory = newCategory
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: CategoryCourseItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Category) {
            binding.tvNameCategory.text = data.name
            Glide.with(binding.root.context)
                .load(data.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivImageCategory)
        }
    }
}