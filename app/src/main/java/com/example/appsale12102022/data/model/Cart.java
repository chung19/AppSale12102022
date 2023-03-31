package com.example.appsale12102022.data.model;

import com.example.appsale12102022.data.remote.dto.ProductDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pphat on 2/6/2023.
 */
public class Cart {

    private String id;
    private List<Product> productList;
    private String idUser;
    private Integer price;
    private String dateCreated;

    public Cart(String id, List<Product> productList, String idUser, Integer price, String dateCreated) {
        this.id = id;
        this.productList = productList;
        this.idUser = idUser;
        this.price = price;
        this.dateCreated = dateCreated;
    }

    public Cart() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", productList=" + productList +
                ", idUser='" + idUser + '\'' +
                ", price=" + price +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
