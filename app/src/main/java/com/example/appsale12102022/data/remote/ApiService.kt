package com.example.appsale12102022.data.remote
import com.example.appsale12102022.data.remote.dto.CartDTO
import com.example.appsale12102022.data.remote.dto.ProductDTO
import com.example.appsale12102022.data.remote.dto.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
interface ApiService {
    @POST("user/sign-in")
    fun signIn(@Body params: HashMap<String, String>): Call<AppResource<UserDTO>>
    @POST("user/sign-up")
    fun signUp(@Body params: HashMap<String, String>): Call<AppResource<UserDTO>>
    @GET("product")
    fun getListProducts(): Call<AppResource<List<ProductDTO>>>
    @GET("cart")
    fun getCart(): Call<AppResource<CartDTO>>
    @POST("cart/add")
    fun addCart(): Call<AppResource<CartDTO>>
}