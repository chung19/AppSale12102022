
package com.example.appsale12102022.data.model
data class Cart(
    var id: String? = null,
    var productList: List<Product>? = null,
    var idUser: String? = null,
    var price: Int? = null,
    var dateCreated: String? = null
) {
    constructor(id: String, productList: List<Product>, idUser: String, price: Int, dateCreated: String) : this() {
        this.id = id
        this.productList = productList
        this.idUser = idUser
        this.price = price
        this.dateCreated = dateCreated
    }
}
