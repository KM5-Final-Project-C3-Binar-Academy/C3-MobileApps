package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.remote.model.request.user.EditUser
import com.c3.mobileapps.data.remote.model.response.user.User
import com.c3.mobileapps.data.remote.service.ApiServiceUser

class UserRepository(private val apiServiceUser: ApiServiceUser) {

    suspend fun getUser(token:String) = apiServiceUser.getUser(token)
    suspend fun updateUser(token: String, updatedField:EditUser) = apiServiceUser.updateUser(token,updatedField)
}