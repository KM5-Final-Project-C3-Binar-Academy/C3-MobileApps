package com.c3.mobileapps.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c3.mobileapps.R
import com.c3.mobileapps.data.listCourses.CoursesData
import com.c3.mobileapps.databinding.ItemKelasBinding
import com.c3.mobileapps.ui.course.CourseFragment


class ListCourseAdapter(private val context: CourseFragment,
                        private var data: List<CoursesData?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemKelasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseHolder(binding)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewCourseHolder = holder as CourseHolder
        viewCourseHolder.bindContent(data[position] as CoursesData)

        Glide.with(context)
            .load(data[position]?.image)
            .into(holder.image)
    }

    override fun getItemCount(): Int = data.size
    @SuppressLint("NotifyDataSetChanged")
    fun setData(datalist: List<CoursesData?>){
        this.data = datalist
        notifyDataSetChanged()
    }

    class CourseHolder(private val binding: ItemKelasBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val image: ImageView = itemView.findViewById(R.id.imageView)
        fun bindContent(listKelas: CoursesData) {
            binding.tvNamaKelas.text = listKelas.name
            binding.tvCategory.text = listKelas.courseCategory?.name
            binding.tvAuthor.text = listKelas.author
            binding.tvLevel.text = listKelas.difficulty
            binding.tvRating.text = listKelas.rating.toString()
            binding.tvDurasi.text = "?"
            binding.tvJumlahModul.text = "?"
        }

    }
}