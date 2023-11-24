package com.c3.mobileapps.data.remote.model.response.course

import com.c3.mobileapps.R

object Kelas {
    val kelas:List<Any>
        get() = mutableListOf(
            ListKelas(R.drawable.metematikan, "Kelas Metmatika", "Free"),
            ListKelas(R.drawable.metematikan, "Kelas IPA", "Free"),
            ListKelas(R.drawable.metematikan, "Kelas IPS", "Free"),
            ListKelas(R.drawable.metematikan, "Kelas Bahasa Indonesia", "Free"),
            ListKelas(R.drawable.metematikan, "Kelas English", "Premium"),
            ListKelas(R.drawable.metematikan, "Kelas Kebudayaan Korea", "Premium"),
            ListKelas(R.drawable.metematikan, "Kelas Kebudayaan Jepang", "Premium"),
            ListKelas(R.drawable.metematikan, "Kelas Kebudayaan Thailand", "Premium"),
        )
}