package com.c3.mobileapps.data.database.courseDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.c3.mobileapps.data.remote.model.response.course.CourseResponse

@Entity(tableName = "course_table")
class TbCourse(var courseResponse: CourseResponse) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}