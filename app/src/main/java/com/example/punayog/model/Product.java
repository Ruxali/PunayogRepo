package com.example.punayog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product  implements Parcelable {
    private String mImageUrl;
    private String productName;
    private String price;
    private String shortDesc;
    private String location;
    private String longDesc;
    private String category;
    private String subCategory;

    public Product(){

    }

    public Product(String mImageUrl, String productName, String price, String shortDesc, String location, String longDesc, String category, String subCategory) {
        this.mImageUrl = mImageUrl;
        this.productName = productName;
        this.price = price;
        this.shortDesc = shortDesc;
        this.location = location;
        this.longDesc = longDesc;
        this.category = category;
        this.subCategory = subCategory;
    }

    protected Product(Parcel in) {
        mImageUrl = in.readString();
        productName = in.readString();
        price = in.readString();
        shortDesc = in.readString();
        location = in.readString();
        longDesc = in.readString();
        category = in.readString();
        subCategory = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mImageUrl);
        parcel.writeString(productName);
        parcel.writeString(price);
        parcel.writeString(shortDesc);
        parcel.writeString(location);
        parcel.writeString(longDesc);
        parcel.writeString(category);
        parcel.writeString(subCategory);
    }

    @Override
    public String toString() {
        return "Product{" +
                "mImageUrl='" + mImageUrl + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", location='" + location + '\'' +
                ", longDesc='" + longDesc + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                '}';
    }
}
