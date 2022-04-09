package com.example.punayog.model;

public class Product {
    private String productImage;
    private String productName;
    private String productPrice;
    private String productShortDesc;
    private String productLocation;

    public Product(String productImage, String productName, String productPrice, String productShortDesc, String productLocation) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productShortDesc = productShortDesc;
        this.productLocation = productLocation;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductShortDesc() {
        return productShortDesc;
    }

    public void setProductShortDesc(String productShortDesc) {
        this.productShortDesc = productShortDesc;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }
}
