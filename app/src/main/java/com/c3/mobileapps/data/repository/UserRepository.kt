package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.remote.model.request.user.EditPassword
import com.c3.mobileapps.data.remote.model.request.user.EditUser
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.remote.model.response.user.User
import com.c3.mobileapps.data.remote.service.ApiServiceUser
import retrofit2.Response

class UserRepository(private val apiServiceUser: ApiServiceUser) {

    suspend fun getUser(token:String) = apiServiceUser.getUser(token)
    suspend fun updateUser(token: String, updatedField:EditUser) = apiServiceUser.updateUser(token,updatedField)
    suspend fun editPass(token: String, updateField: EditPassword) : Response<AuthResponse> = apiServiceUser.updatePassword(token,updateField)
}