package com.c3.mobileapps.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.ItemKelasFullBinding
import com.c3.mobileapps.utils.CourseDiffUtil


class ListCourseAdapter(private var data :List<Course>,  private var listener: (Course) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemKelasFullBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewCourseHolder = holder as CourseHolder
        viewCourseHolder.bindContent(data[position])
        val listenerItem = data[position]

        holder.itemView.setOnClickListener {
            listener(listenerItem)
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(courseResponse: List<Course>) {
        val diffUtil = CourseDiffUtil(data, courseResponse)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = courseResponse
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class CourseHolder(private val binding: ItemKelasFullBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindContent(data: Course) {
            binding.tvNamaKelas.text = data.courseCategory?.name
            binding.deskripsiJudulKelas.text = data.name
            binding.creatorKelas.text = "by ${data.author}"
            binding.levelNameKelas.text = data.difficulty
            binding.rating.text = data.rating
            Glide.with(binding.root.context)
                .load(data.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)

            if (data.premium == true){
                binding.btnMulaiKelas.visibility = View.GONE
                binding.btnPremium.text = "Beli Rp.${data.price}"
            }else{
                binding.btnPremium.visibility = View.GONE
            }
        }

    }
}