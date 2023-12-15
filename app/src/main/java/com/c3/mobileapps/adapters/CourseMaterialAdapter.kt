package com.c3.mobileapps.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.data.remote.model.response.course.CourseChapter
import com.c3.mobileapps.data.remote.model.response.course.CourseMaterial
import com.c3.mobileapps.databinding.ItemMateriBinding
import com.c3.mobileapps.databinding.ItemMateriHeaderBinding

class CourseMaterialAdapter(private val data: List<Any>,private var listener: ((String) -> Unit)? = null)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_HEADER = 0
        private const val ITEM_MATERIAL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is CourseChapter -> ITEM_HEADER
            is CourseMaterial -> ITEM_MATERIAL
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }


    class MaterialViewHolder(private val binding: ItemMateriBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CourseMaterial) {
            binding.tvMateri.text = data.name

        }
    }

    class HeaderViewHolder(private val binding: ItemMateriHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CourseChapter) {
            binding.tvHeader.text = data.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_HEADER -> {
                val binding =
                    ItemMateriHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                val listenerItem = (data[position] as CourseMaterial)
                materialHolder.onBind(listenerItem)

                holder.itemView.setOnClickListener {
                    listener?.invoke(listenerItem.video!!)
                }
            }

            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun getItemCount(): Int = data.size
}