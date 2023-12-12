package com.c3.mobileapps.data.local.database.categoryDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(tbCategory: TbCategory)

    @Query("SELECT * FROM category_table ORDER BY id ASC")
    fun readCategory(): Flow<List<TbCategory>>
}