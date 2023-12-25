package com.c3.mobileapps.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.ItemKelasFullBinding
import com.c3.mobileapps.utils.DiffUtils


class ListCourseAdapter(private var data :List<Course>,
                        private var onItemClick: (Course) -> Unit,
                    private var onBadgelick: (Course) -> Unit)

    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemKelasFullBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewCourseHolder = holder as CourseHolder
        viewCourseHolder.bindContent(data[position], onBadgelick)
        val listenerItem = data[position]

        holder.itemView.setOnClickListener {
            onItemClick(listenerItem)
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(courseResponse: List<Course>) {
        val diffUtil = DiffUtils(data, courseResponse)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = courseResponse
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearData() {
        val diffCallback = DiffUtils(data, emptyList())
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data = emptyList()
        diffResult.dispatchUpdatesTo(this)
    }
    class CourseHolder(private val binding: ItemKelasFullBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindContent(data: Course, onBadgelick: (Course) -> Unit?) {
            binding.tvNamaKelas.text = data.courseCategory?.name
            binding.deskripsiJudulKelas.text = data.name
            binding.creatorKelas.text = "by ${data.author}"
            binding.levelNameKelas.text = data.difficulty
            binding.rating.text = data.rating
            binding.durasiKelas.text = data.totalDuration.toString()
            binding.jumlahModulKelas.text = data.totalMaterials.toString()
            Glide.with(binding.root.context)
                .load(data.image ?: data.courseCategory?.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)

            if (data.premium == true){
                val price = "Beli Rp.${data.price}"
                binding.btnPremium.text = price
                binding.btnPremium.visibility = View.VISIBLE
                binding.btnMulaiKelas.visibility = View.GONE
                binding.btnPremium.setOnClickListener {
                    onBadgelick.invoke(data)
                }

            }else{
                binding.btnPremium.visibility = View.GONE
                binding.btnMulaiKelas.visibility = View.VISIBLE
                binding.btnMulaiKelas.setOnClickListener {
                    onBadgelick.invoke(data)
                }
            }
        }

    }
}