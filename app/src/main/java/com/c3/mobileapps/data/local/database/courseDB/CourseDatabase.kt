package com.c3.mobileapps.data.local.database.courseDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TbCourse::class], version = 3, exportSchema = false)
@TypeConverters(ConverterCourse::class)
abstract class CourseDatabase : RoomDatabase() {
    abstract val courseDao: CourseDao

    companion object {

        @Volatile
        private var INSTANCE: CourseDatabase? = null

        fun getInstance(context: Context): CourseDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CourseDatabase::class.java,
                        "category_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}