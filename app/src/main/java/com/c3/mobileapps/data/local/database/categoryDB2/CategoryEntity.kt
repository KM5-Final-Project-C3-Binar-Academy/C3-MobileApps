package com.c3.mobileapps.data.local.database.categoryDB2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.c3.mobileapps.data.local.model.CategoryLocal

@Entity("table_category")
class CategoryEntity (
    @PrimaryKey(autoGenerate = true)
    var itemId: Long? = null,

    @ColumnInfo(name = "id")
    var id: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "image")
    var image: String? = null
)

fun CategoryEntity.toDomain() = CategoryLocal(itemId = itemId?.toInt(), id = id, name = name, image=image)