package com.c3.mobileapps.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse
import com.c3.mobileapps.databinding.ItemKelasBinding
import com.c3.mobileapps.utils.CourseDiffUtil


class ListCourseAdapter(private var data :List<Course>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemKelasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewCourseHolder = holder as CourseHolder
        viewCourseHolder.bindContent(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(courseResponse: List<Course>) {
        val diffUtil = CourseDiffUtil(data, courseResponse)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = courseResponse
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class CourseHolder(private val binding: ItemKelasBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindContent(listKelas: Course) {
            binding.tvNamaKelas.text = listKelas.name
            binding.tvCategory.text = listKelas.courseCategory?.name
            binding.tvAuthor.text = listKelas.author
            binding.tvLevel.text = listKelas.difficulty
            binding.tvRating.text = listKelas.rating.toString()
            binding.tvDurasi.text = "?"
            binding.tvJumlahModul.text = "?"

            Glide.with(binding.root.context)
                .load(listKelas.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)
        }

    }
}