package com.example.punayog.model;

public class CartModel {
    private String image, name, price,key,buyerName,buyerEmail,buyerNumber,cartId,productId,orderId;

    public CartModel(String orderId,String productId, String cartId,String image, String name, String price,String buyerName,String buyerEmail, String buyerNumber) {
        this.orderId = orderId;
        this.productId = productId;
        this.cartId = cartId;
        this.image = image;
        this.name = name;
        this.price = price;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.buyerNumber = buyerNumber;
    }

    public CartModel() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public String setProductId(String productId) {
        this.productId = productId;
        return productId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public String setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
        return buyerEmail;
    }

    public String getBuyerNumber() {
        return buyerNumber;
    }

    public void setBuyerNumber(String buyerNumber) {
        this.buyerNumber = buyerNumber;
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

//    public double getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(double totalPrice) {
//        this.totalPrice = totalPrice;
//    }
}