package com.c3.mobileapps.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.ItemCourseBinding
import com.c3.mobileapps.databinding.ItemKelasFullBinding
import com.c3.mobileapps.utils.DiffUtils

class KelasAdapter (private var data :List<Course>,
                   private var listener: (Course) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setData(courseResponse: List<Course>) {
        val diffUtil = DiffUtils(data, courseResponse)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = courseResponse
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KelasHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewKelasHolder = holder as KelasHolder
        viewKelasHolder.bindContent(data[position])
        val listenerItem = data[position]

        holder.itemView.setOnClickListener {
            listener(listenerItem)
        }
    }

    class KelasHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindContent(data: Course) {
            binding.tvNamaKelas.text = data.courseCategory?.name
            binding.deskripsiJudulKelas.text = data.name
            binding.creatorKelas.text = "by ${data.author}"
            binding.levelNameKelas.text = data.difficulty
            binding.rating.text = data.rating
            binding.durasiKelas.text = "${ data.totalDuration.toString()} Menit"
            binding.jumlahModulKelas.text =  "${ data.totalMaterials.toString()} Modul"
            Glide.with(binding.root.context)
                .load(data.image ?: data.courseCategory?.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)

            binding.progressBar.max = data.totalMaterials ?: 0
            binding.progressBar.progress = data.totalCompletedMaterial ?: 0
            val percentageCompleted = (data.totalCompletedMaterial ?: 0) * 100 / (data.totalMaterials ?: 1)
            binding.tvProgres.text = "$percentageCompleted% Completed"


        }

    }

}