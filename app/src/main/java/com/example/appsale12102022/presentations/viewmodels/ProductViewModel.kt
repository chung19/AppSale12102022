package com.example.appsale12102022.presentations.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appsale12102022.data.model.Cart
import com.example.appsale12102022.data.model.Product
import com.example.appsale12102022.data.remote.AppResource
import com.example.appsale12102022.data.remote.AppResource.Loading
import com.example.appsale12102022.data.remote.dto.CartDTO
import com.example.appsale12102022.data.remote.dto.ProductDTO
import com.example.appsale12102022.data.repositories.CartRepository
import com.example.appsale12102022.data.repositories.ProductRepository
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Created by pphat on 2/6/2023.
 */
class ProductViewModel(context: Context?) : ViewModel() {
    private val productRepository: ProductRepository
    private val cartRepository: CartRepository
    val productResource = MutableLiveData<AppResource<List<Product>?>>()
    val cartResource = MutableLiveData<AppResource<Cart>>()

    init {
        productRepository = ProductRepository(context!!)
        cartRepository = CartRepository(context)
    }

    fun getProductResource(): LiveData<AppResource<List<Product>?>> {
        return productResource
    }

    fun getCartResource(): LiveData<AppResource<Cart>> {
        return cartResource
    }

    fun fetchListProducts() {
        productResource.value = Loading(null)
        productRepository.getListProducts()
            .enqueue(object : Callback<AppResource<List<ProductDTO>>> {
                override fun onResponse(
                    call: Call<AppResource<List<ProductDTO>>>,
                    response: Response<AppResource<List<ProductDTO>>>
                ) {
                    if (response.isSuccessful) {
                        val productDTOList = response.body()!!.data!!
                        val productList: MutableList<Product> = ArrayList()
                        for ((id, name, address, price, img, quantity, gallery) in productDTOList) {
                            productList.add(
                                Product(
                                    id,
                                    name,
                                    address,
                                    price,
                                    img,
                                    quantity,
                                    gallery
                                )
                            )
                        }
                        productResource.setValue(AppResource.Success(productList))
                    } else {
                        if (response.errorBody() == null) return
                        try {
                            val jsonObject = JSONObject(response.errorBody()!!.string())
                            val message = jsonObject.getString("message")
                            productResource.setValue(AppResource.Error(message))
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<AppResource<List<ProductDTO>>>, t: Throwable) {
                    productResource.value = AppResource.Error(
                        t.message!!
                    )
                }
            })
    }


    fun fetchCart() {
        cartResource.value = Loading(null)
        cartRepository.getCart().enqueue(object : Callback<AppResource<CartDTO>> {
            override fun onResponse(
                call: Call<AppResource<CartDTO>>,
                response: Response<AppResource<CartDTO>>
            ) {
                if (response.isSuccessful) {
                    val cartDTO = response.body()!!.data!!
                    val productDTOList = cartDTO.productDTOS
                    val productList: MutableList<Product> = ArrayList()
                    for ((id, name, address, price, img, quantity, gallery, dateCreated, dateUpdated) in productDTOList) {
                        productList.add(
                            Product(
                                id,
                                name,
                                address,
                                price,
                                img,
                                quantity,
                                gallery
                            )
                        )
                    }
                    val cart = Cart(
                        cartDTO.id,
                        productList,
                        cartDTO.idUser,
                        cartDTO.price,
                        cartDTO.dateCreated
                    )
                    cartResource.setValue(AppResource.Success(cart))
                } else {
                    if (response.errorBody() == null) return
                    try {
                        val jsonObject = JSONObject(response.errorBody()!!.string())
                        val message = jsonObject.getString("message")
                        cartResource.setValue(AppResource.Error(message))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<AppResource<CartDTO>>, t: Throwable) {
                cartResource.value = AppResource.Error(t.message!!)
            }
        })
    }
}