package com.c3.mobileapps.data.local.database.courseDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(tbcourse: TbCourse)

    @Query("SELECT * FROM course_table ORDER BY id ASC")
    fun readCourse(): Flow<List<TbCourse>>
}