package com.example.appsale12102022.data.repositories

import android.content.Context
import com.example.appsale12102022.data.remote.ApiService
import com.example.appsale12102022.data.remote.AppResource
import com.example.appsale12102022.data.remote.RetrofitClient
import com.example.appsale12102022.data.remote.dto.UserDTO
import retrofit2.Call

/**
 * Created by pphat on 2/1/2023.
 */
class AuthenticationRepository(context: Context) {
    private val apiService: ApiService = RetrofitClient.getInstance(context).apiService

    fun signIn(email: String, password: String): Call<AppResource<UserDTO>> {
        val hashMap = hashMapOf<String, String>()
        hashMap["email"] = email
        hashMap["password"] = password
        return apiService.signIn(hashMap)
    }

    fun signUp(email: String, password: String, name: String, phone: String, address: String): Call<AppResource<UserDTO>> {
        val hashMap = hashMapOf<String, String>()
        hashMap["email"] = email
        hashMap["password"] = password
        hashMap["name"] = name
        hashMap["phone"] = phone
        hashMap["address"] = address
        return apiService.signUp(hashMap)
    }
}