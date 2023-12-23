package com.c3.mobileapps.data.local.database.categoryDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.c3.mobileapps.data.remote.model.response.course.CategoryResponse


@Entity(tableName = "category_table")
class TbCategory(var categoryResponse: CategoryResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    var createdTime: String? = null
}