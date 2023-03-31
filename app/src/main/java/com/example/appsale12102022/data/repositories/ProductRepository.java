package com.example.appsale12102022.data.repositories;

import android.content.Context;

import com.example.appsale12102022.data.remote.ApiService;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.data.remote.RetrofitClient;
import com.example.appsale12102022.data.remote.dto.ProductDTO;

import java.util.List;

import retrofit2.Call;

/**
 * Created by pphat on 2/6/2023.
 */
public class ProductRepository {
    private ApiService apiService;

    public ProductRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<List<ProductDTO>>> getListProducts() {
        return apiService.getListProducts();
    }
}
