package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.database.categoryDB.CategoryDao
import com.c3.mobileapps.data.database.categoryDB.TbCategory
import com.c3.mobileapps.data.database.courseDB.CourseDao
import com.c3.mobileapps.data.database.courseDB.TbCourse
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val courseDao: CourseDao, private val categoryDao: CategoryDao) {

    fun readCourse(): Flow<List<TbCourse>> {
        return courseDao.readCourse()
    }

    suspend fun insertCourse(tbCourse: TbCourse) {
        courseDao.insertCourse(tbCourse)
    }

    fun readCategory(): Flow<List<TbCategory>> {
        return categoryDao.readCategory()
    }

    suspend fun insertCategory(tbCategory: TbCategory) {
        categoryDao.insertCategory(tbCategory)
    }
}