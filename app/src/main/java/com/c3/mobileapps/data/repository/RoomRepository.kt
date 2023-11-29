package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.database.courseDB.CourseDao
import com.c3.mobileapps.data.database.courseDB.TbCourse
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val courseDao: CourseDao) {

    fun readCourse(): Flow<List<TbCourse>> {
        return courseDao.readCourse()
    }

    suspend fun insertCourse(tbCourse: TbCourse) {
        courseDao.insertCourse(tbCourse)
    }
}