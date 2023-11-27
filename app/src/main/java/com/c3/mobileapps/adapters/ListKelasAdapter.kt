package com.c3.mobileapps.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.data.remote.model.response.course.ListKelas
import com.c3.mobileapps.databinding.ItemKelasBinding

class ListKelasAdapter(private val data: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemKelasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewCourseHolder = holder as CourseHolder
        viewCourseHolder.bindContent(data[position] as ListKelas)
    }

    class CourseHolder(private val binding: ItemKelasBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindContent(listKelas: ListKelas) {
            binding.tvNamaKelas.text = listKelas.namaKelas
            binding.imageView.setImageResource(listKelas.imgMenu)
        }

    }
}