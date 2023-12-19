package com.c3.mobileapps.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.c3.mobileapps.data.remote.model.response.payment.Payment
import com.c3.mobileapps.databinding.ItemHistoryPaymentBinding
import com.c3.mobileapps.utils.DiffUtils

class HistoryAdapter (private var data :List<Payment>,
                      private var listener: (Payment) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setData(paymentResp: List<Payment>) {
        val diffUtil = DiffUtils(data, paymentResp)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = paymentResp
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemHistoryPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewKelasHolder = holder as HistoryHolder
        viewKelasHolder.bindContent(data[position])
        val listenerItem = data[position]

        holder.itemView.setOnClickListener {
            listener(listenerItem)
        }
    }

    class HistoryHolder(private val binding: ItemHistoryPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindContent(data: Payment) {

            binding.tvNamaKelas.text = data.course?.courseCategory?.name
            binding.deskripsiJudulKelas.text = data.course?.name
            binding.creatorKelas.text = "by ${data.course?.author}"
            binding.levelNameKelas.text = data.course?.difficulty
            binding.rating.text = data.course?.rating
            Glide.with(binding.root.context)
                .load(data.course?.image ?: data.course?.courseCategory?.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)

            when(data.status){
                "COMPLETED" ->{
                    binding.icPaid.visibility = View.VISIBLE
                    binding.icWaitingPayment.visibility = View.GONE
                }
                "PENDING" ->{
                    binding.icPaid.visibility = View.GONE
                    binding.icWaitingPayment.visibility = View.VISIBLE
                }
            }


        }

    }

}