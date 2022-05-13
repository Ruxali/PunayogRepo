package com.example.punayog.model;

public class HorizontalScrollModel {

    private String productImage;
    private String productTitle;
    private String productShortDesc;
    private String productPrice;

    public HorizontalScrollModel(String productImage, String productTitle, String productShortDesc, String productPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productShortDesc = productShortDesc;
        this.productPrice = productPrice;
    }

    public HorizontalScrollModel() {

    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductShortDesc() {
        return productShortDesc;
    }

    public void setProductShortDesc(String productShortDesc) {
        this.productShortDesc = productShortDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
