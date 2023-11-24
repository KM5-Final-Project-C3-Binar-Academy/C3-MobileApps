package com.c3.mobileapps.data.remote.model.response.course

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListKelas (
    var imgMenu: Int,
    var namaKelas: String,
    var typeKelas : String
): Parcelable

