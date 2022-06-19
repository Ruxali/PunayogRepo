package com.example.punayog.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Upload {
    private String status;
    private String productId;
    private String mImageUrl;
    private String productName;
    private String price;
    private String shortDesc;
    private String longDesc;
    private String location;
    private String category;
    private String subCategory;
    private String sellerName;
    private String sellerNumber;
    private String sellerEmail;

    Upload(){}

    public Upload(String status,String productId,String mImageUrl, String productName, String price, String shortDesc, String longDesc, String location, String category, String subCategory, String sellerName, String sellerNumber, String sellerEmail) {
        this.status = status;
        this.productId = productId;
        this.mImageUrl = mImageUrl;
        this.productName = productName;
        this.price = price;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.location = location;
        this.category = category;
        this.subCategory = subCategory;
        this.sellerName = sellerName;
        this.sellerNumber = sellerNumber;
        this.sellerEmail = sellerEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }
}
