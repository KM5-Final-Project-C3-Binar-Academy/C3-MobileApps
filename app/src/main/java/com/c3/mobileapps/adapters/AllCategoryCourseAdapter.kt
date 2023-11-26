package com.c3.mobileapps.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.data.remote.model.response.course.CategoryCourse
import com.c3.mobileapps.databinding.CategoryCourseItemBinding
import com.c3.mobileapps.databinding.GridCategoryCourseItemBinding

class AllCategoryCourseAdapter(private val listCategory: List<CategoryCourse>) : RecyclerView.Adapter<AllCategoryCourseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GridCategoryCourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listCategory[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = listCategory.size

    class ViewHolder(private val binding: GridCategoryCourseItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: CategoryCourse) {
            binding.tvCategory.text = data.name
        }
    }
}