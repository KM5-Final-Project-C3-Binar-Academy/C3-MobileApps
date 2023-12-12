package com.c3.mobileapps.utils

import androidx.recyclerview.widget.DiffUtil

class DiffUtils <T>(
    private val oldCourse: List<T>,
    private val newCourse: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldCourse.size
    }

    override fun getNewListSize(): Int {
        return newCourse.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCourse[oldItemPosition] === newCourse[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCourse[oldItemPosition] == newCourse[newItemPosition]
    }

}