package com.c3.mobileapps.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.data.remote.model.response.notification.Notification
import com.c3.mobileapps.databinding.ItemNotificationBinding
import com.c3.mobileapps.utils.DiffUtils
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class NotifAdapter(private var data:List<Notification>,
                   private var listener: ((Notification) -> Unit)?
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setData(notifResp: List<Notification>) {
        val diffUtil = DiffUtils(data, notifResp)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = notifResp
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotifHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewKelasHolder = holder as NotifHolder
        viewKelasHolder.bindContent(data[position])
        val listenerItem = data[position]

        holder.itemView.setOnClickListener {
            listener?.invoke(listenerItem)
        }
    }

    class NotifHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindContent(data: Notification) {

            binding.tvTitleNotif.text = data.name
            binding.DescNotif.text = data.description
            binding.TextClockNotif.text = convertDateFormat(data.createdAt!!)

            if (data.viewed == true){
                binding.StatusBarGreen.visibility = View.VISIBLE
                binding.StatusBarRed.visibility = View.INVISIBLE
            }else{
                binding.StatusBarGreen.visibility = View.INVISIBLE
                binding.StatusBarRed.visibility = View.VISIBLE
            }

        }


        private fun convertDateFormat(dateTime: String): String {
            val formatter = DateTimeFormatter.ofPattern("dd MMM, HH:mm", Locale.getDefault())
            val zonedDateTime = ZonedDateTime.parse(dateTime)
            return formatter.format(zonedDateTime)
        }

    }
}