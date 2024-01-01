package com.c3.mobileapps.data.repository

import com.c3.mobileapps.data.remote.model.request.user.EditPassword
import com.c3.mobileapps.data.remote.model.request.user.EditUser
import com.c3.mobileapps.data.remote.model.response.user.AuthResponse
import com.c3.mobileapps.data.remote.service.ApiServiceUser
import com.c3.mobileapps.utils.Resource
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class UserRepository(private val apiServiceUser: ApiServiceUser) {

    suspend fun getUser(token:String) = apiServiceUser.getUser(token)
    suspend fun updateUser(token: String, updatedField:EditUser): AuthResponse {
        val nameRequestBody  = updatedField.name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val emailRequestBody = updatedField.email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val phoneRequestBody = updatedField.phone_number.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val imageRequestBody = updatedField.image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", updatedField.image.name, imageRequestBody)

        return apiServiceUser.updateUser(token,nameRequestBody, emailRequestBody, phoneRequestBody, imagePart)
    }
    suspend fun editPass(token: String, updateField: EditPassword) : Response<AuthResponse> = apiServiceUser.updatePassword(token,updateField)
}