package com.c3.mobileapps.data.local.database.categoryDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.c3.mobileapps.data.local.database.categoryDB2.CategoryEntity

@TypeConverters(ConverterCategory::class)
@Database(entities = [TbCategory::class], version = 3, exportSchema = false)
abstract class CategoryDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDao

    companion object {

        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        fun getInstance(context: Context): CategoryDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CategoryDatabase::class.java,
                        "category_course_database"
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