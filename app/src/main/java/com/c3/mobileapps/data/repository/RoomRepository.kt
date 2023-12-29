package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.local.database.categoryDB.CategoryDao
import com.c3.mobileapps.data.local.database.categoryDB.TbCategory
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val categoryDao: CategoryDao) {

    fun readCategory(): Flow<List<TbCategory>> {
        return categoryDao.readCategory()
    }

}