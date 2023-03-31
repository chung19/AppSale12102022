package com.example.appsale12102022.data.repositories
import android.content.Context
import com.example.appsale12102022.data.remote.ApiService
import com.example.appsale12102022.data.remote.AppResource
import com.example.appsale12102022.data.remote.RetrofitClient
import com.example.appsale12102022.data.remote.dto.ProductDTO
import retrofit2.Call
class ProductRepository(context: Context) {
    private val apiService: ApiService = RetrofitClient.getInstance(context).apiService
    fun getListProducts(): Call<AppResource<List<ProductDTO>>> {
        return apiService.getListProducts()
    }
}