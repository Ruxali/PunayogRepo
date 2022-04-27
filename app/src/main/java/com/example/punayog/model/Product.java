package com.example.punayog.model;

public class Product {
    private String mImageUrl;
    private String productName;
    private String price;
    private String shortDesc;
    private String location;
    private String longDesc;

    public Product(){

    }

    public Product(String mImageUrl, String productName, String price, String shortDesc, String location, String longDesc) {
        this.mImageUrl = mImageUrl;
        this.productName = productName;
        this.price = price;
        this.shortDesc = shortDesc;
        this.location = location;
        this.longDesc = longDesc;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }
}
