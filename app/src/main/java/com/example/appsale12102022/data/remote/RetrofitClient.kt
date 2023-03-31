package com.example.appsale12102022.data.remote
import android.content.Context
import com.example.appsale12102022.common.AppConstant
import com.example.appsale12102022.data.local.SharePrefApp
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
class RetrofitClient private constructor(context: Context) {
    private val retrofit: Retrofit
    internal val apiService: ApiService
    init {
        retrofit = createRetrofit(context)
        apiService = retrofit.create(ApiService::class.java)
    }
    companion object {
        private var instance: RetrofitClient? = null
        fun getInstance(context: Context): RetrofitClient {
            if (instance == null) {
                instance = RetrofitClient(context)
            }
            return instance!!
        }
    }
    private fun createRetrofit(context: Context): Retrofit {
        val logRequest = HttpLoggingInterceptor()
        logRequest.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logRequest)
            .addInterceptor(Interceptor { chain ->
                val token = SharePrefApp.getInstance(context).getDataString(AppConstant.KEY_TOKEN)
                if (token != null && token.isNotEmpty()) {
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    return@Interceptor chain.proceed(newRequest)
                }
                chain.proceed(chain.request())
            }).build()
        val gson = GsonBuilder().create()
        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    fun getApiService(): ApiService {
        return apiService
    }
}