package com.c3.mobileapps.data.repository


import com.c3.mobileapps.data.local.database.categoryDB2.CategoryEntity
import com.c3.mobileapps.data.local.database.categoryDB2.Categorydao
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val categorydao: Categorydao) {

    fun readCategory2(): Flow<List<CategoryEntity>> {
        return categorydao.readCategory()
    }

}