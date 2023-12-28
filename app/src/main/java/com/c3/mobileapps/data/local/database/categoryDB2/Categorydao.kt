package com.c3.mobileapps.data.local.database.categoryDB2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface Categorydao {
    @Insert
    fun insert(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM table_category ORDER BY itemId DESC")
    fun getAllItem(): List<CategoryEntity>

    @Delete
    fun delete(categoryEntity: CategoryEntity)

    @Query("DELETE FROM table_category WHERE itemId = :idParams")
    fun deleteById(idParams: Long)

    @Query("DELETE FROM table_category")
    fun deleteAll()
}