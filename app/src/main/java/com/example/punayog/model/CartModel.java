package com.example.punayog.model;

public class CartModel {
    private String image, name, price,key;
    private float totalPrice;

    public CartModel(String image, String name, String price, float totalPrice) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public CartModel() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}