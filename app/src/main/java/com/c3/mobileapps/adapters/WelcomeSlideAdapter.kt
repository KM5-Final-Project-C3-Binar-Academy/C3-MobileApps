package com.c3.mobileapps.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.R
import com.c3.mobileapps.data.local.model.IntroSlide
import com.c3.mobileapps.data.remote.model.response.notification.Notification
import com.c3.mobileapps.databinding.ItemNotificationBinding
import com.c3.mobileapps.databinding.SlideItemPagerBinding

class WelcomeSlideAdapter(private val introSlide: List<IntroSlide>): RecyclerView.Adapter<WelcomeSlideAdapter.WelcomeSlideViewHolder>(){
    inner class WelcomeSlideViewHolder(private val binding: SlideItemPagerBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bindContent(data: IntroSlide) {
//            binding.icLogo.setImageResource(data.logo)
            binding.icWelcome.setImageResource(data.gambar)
            binding.learnify.text = data.nama
            binding.text.text = data.desc
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeSlideViewHolder {
        val binding =
            SlideItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WelcomeSlideViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return introSlide.size
    }

    override fun onBindViewHolder(holder: WelcomeSlideViewHolder, position: Int) {
        holder.bindContent(introSlide[position])
    }

}