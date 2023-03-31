package com.example.appsale12102022.data.repositories
import android.content.Context
import com.example.appsale12102022.data.remote.ApiService
import com.example.appsale12102022.data.remote.AppResource
import com.example.appsale12102022.data.remote.RetrofitClient
import com.example.appsale12102022.data.remote.dto.CartDTO
import retrofit2.Call

class CartRepository(context: Context) {
    private val apiService: ApiService = RetrofitClient.getInstance(context).apiService
    fun getCart(): Call<AppResource<CartDTO>> {
        return apiService.getCart()
    }
    fun addCart(): Call<AppResource<CartDTO>> {
        return apiService.addCart()
    }
}