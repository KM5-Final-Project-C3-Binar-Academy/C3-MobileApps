package com.c3.mobileapps.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


open class PagerAdapter(
    fragmentActivity: FragmentActivity,
    private var listfragment: ArrayList<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = listfragment.size

    override fun createFragment(position: Int): Fragment {

        return listfragment[position]
    }

}