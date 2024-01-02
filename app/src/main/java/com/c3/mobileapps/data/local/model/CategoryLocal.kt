package com.c3.mobileapps.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryLocal(
    var itemId: Int?,
    var id: String?,
    var name: String?,
    var image: String?,
): Parcelable