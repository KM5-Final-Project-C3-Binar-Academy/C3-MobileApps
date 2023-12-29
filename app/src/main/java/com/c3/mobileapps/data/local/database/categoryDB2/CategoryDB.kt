package com.c3.mobileapps.data.local.database.categoryDB2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class], version = 5)
abstract class CategoryDB: RoomDatabase() {
    abstract val categorydao: Categorydao
    companion object {

        @Volatile
        private var INSTANCE: CategoryDB? = null
        fun getInstance(context: Context): CategoryDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CategoryDB::class.java,
                        "category_database2"
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