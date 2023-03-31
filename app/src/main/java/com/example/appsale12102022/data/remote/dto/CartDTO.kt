
package com.example.appsale12102022.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by pphat on 2/6/2023.
 */
data class CartDTO(

    @SerializedName("_id")
    @Expose
    val id: String,

    @SerializedName("products")
    @Expose
    val productDTOS: List<ProductDTO>,

    @SerializedName("id_user")
    @Expose
    val idUser: String,

    @SerializedName("price")
    @Expose
    val price: Int,

    @SerializedName("date_created")
    @Expose
    val dateCreated: String
)

