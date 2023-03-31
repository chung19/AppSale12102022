
package com.example.appsale12102022.data.remote.dto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("_id") @Expose val id: String,
    val name: String,
    val address: String,
    val price: Int,
    val img: String,
    val quantity: Int,
    val gallery: List<String>,
    @SerializedName("date_created") @Expose val dateCreated: String,
    @SerializedName("date_updated") @Expose val dateUpdated: String
) {
    constructor() : this("", "", "", 0, "", 0, emptyList(), "", "")
}
