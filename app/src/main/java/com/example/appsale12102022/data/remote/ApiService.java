package com.example.appsale12102022.data.remote;

import com.example.appsale12102022.data.remote.dto.CartDTO;
import com.example.appsale12102022.data.remote.dto.ProductDTO;
import com.example.appsale12102022.data.remote.dto.UserDTO;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("user/sign-in")
    Call<AppResource<UserDTO>> signIn(@Body HashMap<String, String> params);

    @POST("user/sign-up")
    Call<AppResource<UserDTO>> signUp(@Body HashMap<String, String> params);

    @GET("product")
    Call<AppResource<List<ProductDTO>>> getListProducts();

    @GET("cart")
    Call<AppResource<CartDTO>> getCart();
}
