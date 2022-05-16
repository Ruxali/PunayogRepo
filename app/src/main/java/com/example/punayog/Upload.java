package com.example.punayog;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Upload {
    private String mImageUrl;
    private String productName;
    private String price;
    private String shortDesc;
    private String longDesc;
    private String location;
    private String category;

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

    private String subCategory;
    Upload(){}

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

    public Upload( String productName, String price, String shortDesc, String longDesc, String location,String category, String subCategory)
    {
        this.productName=productName;
        this.price=price;
        this.shortDesc=shortDesc;
        this.longDesc=longDesc;
        this.location=location;
        this.category=category;
        this.subCategory = subCategory;
    }


}
