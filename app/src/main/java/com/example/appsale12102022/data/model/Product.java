package com.example.appsale12102022.data.model;

import java.util.List;

/**
 * Created by pphat on 2/6/2023.
 */
public class Product {
    private String id;
    private String name;
    private String address;
    private Integer price;
    private String img;
    private Integer quantity;
    private List<String> gallery;

    public Product() {}

    public Product(String id, String name, String address, Integer price, String img, Integer quantity, List<String> gallery) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.img = img;
        this.quantity = quantity;
        this.gallery = gallery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", quantity=" + quantity +
                ", gallery=" + gallery +
                '}';
    }
}
