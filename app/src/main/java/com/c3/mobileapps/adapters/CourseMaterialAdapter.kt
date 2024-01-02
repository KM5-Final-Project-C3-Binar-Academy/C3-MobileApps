package com.c3.mobileapps.adapters


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.data.remote.model.response.course.CourseChapter
import com.c3.mobileapps.data.remote.model.response.course.MateriKursus
import com.c3.mobileapps.databinding.ItemMateriBinding
import com.c3.mobileapps.databinding.ItemMateriHeaderBinding

class CourseMaterialAdapter(
    private val data: List<Any>,
    private var listener: ((Any) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var enrolled: Boolean = false

    fun setEnrolled(isEnrolled: Boolean) {
        enrolled = isEnrolled
    }

    companion object {
        private const val ITEM_HEADER = 0
        private const val ITEM_MATERIAL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is CourseChapter -> ITEM_HEADER
            is MateriKursus -> ITEM_MATERIAL
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }


    class MaterialViewHolder(private val binding: ItemMateriBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(data: MateriKursus, enrolled: Boolean, listener: ((Any) -> Unit)?) {

            if (enrolled) {
                binding.tvMateri.text = data.materi?.name
                binding.tvMateri.text = data.materi?.name
                binding.tvListNumber.text = data.materi?.orderIndex.toString()
                binding.root.setOnClickListener {
                    data.materi?.let { it1 -> listener?.invoke(it1) }
                    Log.e("check listener", data.materi?.video.toString())
                }

                val isCompleted = data.materi?.courseMaterialStatus?.first()?.completed
                if (isCompleted == true) {
                    //set icon to checklist
                    binding.btnPlay.visibility = View.GONE
                    binding.btnDone.visibility = View.VISIBLE
                }
            } else {
                if (data.idKursus >= 2) {
                    binding.tvListNumber.text = data.materi?.orderIndex.toString()
                    binding.btnPlay.visibility = View.GONE
                    binding.btnDone.visibility = View.GONE
                    binding.btnLock.visibility = View.VISIBLE
                    binding.root.setOnClickListener {
                        listener?.invoke(false)
                    }
                } else {
                    binding.tvMateri.text = data.materi?.name
                    binding.root.setOnClickListener {
                        data.materi?.video?.let { it1 -> listener?.invoke(it1) }
                        Log.e("check listener", data.materi?.video.toString())
                    }
                }
            }

        }
    }

    class HeaderViewHolder(private val binding: ItemMateriHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(data: CourseChapter) {
            binding.tvHeader.text = data.name
            binding.tvHeader.text = data.name
            binding.tvChapterSize.text = "Chapter ${data.orderIndex.toString()}"
            binding.tvTotalMenit.text = data.duration.toString()

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_HEADER -> {
                val binding =
                    ItemMateriHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                HeaderViewHolder(binding)
            }

            ITEM_MATERIAL -> {
                val binding =
                    ItemMateriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MaterialViewHolder(binding)
            }

            else -> throw throw IllegalArgumentException("Undefined view type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_HEADER -> {
                val headerHolder = holder as HeaderViewHolder
                headerHolder.onBind(data[position] as CourseChapter)

            }

            ITEM_MATERIAL -> {
                val materialHolder = holder as MaterialViewHolder
                val listenerItem = (data[position] as MateriKursus)
                materialHolder.onBind(listenerItem, enrolled, listener)
            }

            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun getItemCount(): Int = data.size
}