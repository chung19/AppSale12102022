package com.example.appsale12102022.data.model
data class Product(
    var id: String? = null,
    var name: String? = null,
    var address: String? = null,
    var price: Int? = null,
    var img: String? = null,
    var quantity: Int? = null,
    var gallery: List<String>? = null
) {
    constructor(
        id: String,
        name: String,
        address: String,
        price: Int,
        img: String,
        quantity: Int,
        gallery: List<String>
    ) : this() {
        this.id = id
        this.name = name
        this.address = address
        this.price = price
        this.img = img
        this.quantity = quantity
        this.gallery = gallery
    }
}
