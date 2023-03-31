package com.example.appsale12102022.data.repositories;

import android.content.Context;

import com.example.appsale12102022.data.remote.ApiService;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.data.remote.RetrofitClient;
import com.example.appsale12102022.data.remote.dto.UserDTO;

import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by pphat on 2/1/2023.
 */
public class AuthenticationRepository {
    private ApiService apiService;

    public AuthenticationRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<UserDTO>> signIn(String email, String password){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", password);
        return apiService.signIn(hashMap);
    }

    public Call<AppResource<UserDTO>> signUp(String email, String password, String name, String phone, String address){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", password);
        hashMap.put("name", name);
        hashMap.put("phone", phone);
        hashMap.put("address", address);
        return apiService.signUp(hashMap);
    }
}
