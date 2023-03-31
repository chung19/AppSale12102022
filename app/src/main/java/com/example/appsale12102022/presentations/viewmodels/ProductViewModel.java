package com.example.appsale12102022.presentations.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale12102022.data.model.Cart;
import com.example.appsale12102022.data.model.Product;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.data.remote.dto.CartDTO;
import com.example.appsale12102022.data.remote.dto.ProductDTO;
import com.example.appsale12102022.data.repositories.CartRepository;
import com.example.appsale12102022.data.repositories.ProductRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pphat on 2/6/2023.
 */
public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private MutableLiveData<AppResource<List<Product>>> productResource = new MutableLiveData<>();
    private MutableLiveData<AppResource<Cart>> cartResource = new MutableLiveData<>();

    public ProductViewModel(Context context) {
        productRepository = new ProductRepository(context);
        cartRepository = new CartRepository(context);
    }

    public LiveData<AppResource<List<Product>>> getProductResource() {
        return productResource;
    }

    public LiveData<AppResource<Cart>> getCartResource() {
        return cartResource;
    }

    public void fetchListProducts() {
        productResource.setValue(new AppResource.Loading<>(null));
        productRepository.getListProducts().enqueue(new Callback<AppResource<List<ProductDTO>>>() {
            @Override
            public void onResponse(Call<AppResource<List<ProductDTO>>> call, Response<AppResource<List<ProductDTO>>> response) {
                if (response.isSuccessful()) {
                    List<ProductDTO> productDTOList = response.body().data;
                    List<Product> productList = new ArrayList<>();

                    for (ProductDTO productDTO: productDTOList) {
                        productList.add(new Product(
                                productDTO.getId(),
                                productDTO.getName(),
                                productDTO.getAddress(),
                                productDTO.getPrice(),
                                productDTO.getImg(),
                                productDTO.getQuantity(),
                                productDTO.getGallery()
                        ));
                    }

                    productResource.setValue(new AppResource.Success<>(productList));
                } else {
                    if (response.errorBody() == null) return;
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        productResource.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<List<ProductDTO>>> call, Throwable t) {
                productResource.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

    public void fetchCart() {
        cartResource.setValue(new AppResource.Loading<>(null));
        cartRepository.getCart().enqueue(new Callback<AppResource<CartDTO>>() {
            @Override
            public void onResponse(Call<AppResource<CartDTO>> call, Response<AppResource<CartDTO>> response) {
                if (response.isSuccessful()) {
                    CartDTO cartDTO = response.body().data;

                    List<Product> productList = new ArrayList<>();
                    for (ProductDTO productDTO : cartDTO.getProductDTOS()) {
                        productList.add(new Product(
                                productDTO.getId(),
                                productDTO.getName(),
                                productDTO.getAddress(),
                                productDTO.getPrice(),
                                productDTO.getImg(),
                                productDTO.getQuantity(),
                                productDTO.getGallery()
                        ));
                    }

                    cartResource.setValue(new AppResource.Success<>(
                            new Cart(
                                cartDTO.getId(),
                                productList,
                                cartDTO.getIdUser(),
                                cartDTO.getPrice(),
                                cartDTO.getDateCreated()
                    )));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        if (message.equals("Empty data")) {
                            cartResource.setValue(new AppResource.Success<>(new Cart()));
                        }
                        cartResource.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<CartDTO>> call, Throwable t) {
                cartResource.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
