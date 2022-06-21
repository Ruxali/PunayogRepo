package com.example.punayog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    private String orderStatus,billingAddress, billingEmail, billingName, billingNumber,currentDate,currentTime,orderId,orderedBuyerEmail,orderedProductId,orderedProductName,orderedProductPrice,productImage,sellerEmail,shippingAddress,shippingName,shippingNumber,totalPrice;

    public Order(String orderStatus, String billingAddress, String billingEmail, String billingName, String billingNumber, String currentDate, String currentTime, String orderId, String orderedBuyerEmail, String orderedProductId, String orderedProductName, String orderedProductPrice, String productImage, String sellerEmail, String shippingAddress, String shippingName, String shippingNumber, String totalPrice) {
        this.orderStatus = orderStatus;
        this.billingAddress = billingAddress;
        this.billingEmail = billingEmail;
        this.billingName = billingName;
        this.billingNumber = billingNumber;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.orderId = orderId;
        this.orderedBuyerEmail = orderedBuyerEmail;
        this.orderedProductId = orderedProductId;
        this.orderedProductName = orderedProductName;
        this.orderedProductPrice = orderedProductPrice;
        this.productImage = productImage;
        this.sellerEmail = sellerEmail;
        this.shippingAddress = shippingAddress;
        this.shippingName = shippingName;
        this.shippingNumber = shippingNumber;
        this.totalPrice = totalPrice;
    }

    public Order() {

    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingNumber() {
        return billingNumber;
    }

    public void setBillingNumber(String billingNumber) {
        this.billingNumber = billingNumber;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderedBuyerEmail() {
        return orderedBuyerEmail;
    }

    public void setOrderedBuyerEmail(String orderedBuyerEmail) {
        this.orderedBuyerEmail = orderedBuyerEmail;
    }

    public String getOrderedProductId() {
        return orderedProductId;
    }

    public void setOrderedProductId(String orderedProductId) {
        this.orderedProductId = orderedProductId;
    }

    public String getOrderedProductName() {
        return orderedProductName;
    }

    public void setOrderedProductName(String orderedProductName) {
        this.orderedProductName = orderedProductName;
    }

    public String getOrderedProductPrice() {
        return orderedProductPrice;
    }

    public void setOrderedProductPrice(String orderedProductPrice) {
        this.orderedProductPrice = orderedProductPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingNumber() {
        return shippingNumber;
    }

    public void setShippingNumber(String shippingNumber) {
        this.shippingNumber = shippingNumber;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Order(Parcel in) {
        orderStatus = in.readString();
        orderId = in.readString();
        billingAddress = in.readString();
        billingEmail = in.readString();
        billingName = in.readString();
        billingNumber = in.readString();
        currentDate = in.readString();
        currentTime = in.readString();
        orderedBuyerEmail = in.readString();
        orderedProductId = in.readString();
        sellerEmail = in.readString();
        orderedProductName = in.readString();
        orderedProductPrice = in.readString();
        productImage = in.readString();
        shippingAddress = in.readString();
        shippingName = in.readString();
        shippingNumber = in.readString();
        totalPrice = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderStatus);
        parcel.writeString(orderId);
        parcel.writeString(billingAddress);
        parcel.writeString(billingEmail);
        parcel.writeString(billingName);
        parcel.writeString(billingNumber);
        parcel.writeString(currentDate);
        parcel.writeString(currentTime);
        parcel.writeString(orderedBuyerEmail);
        parcel.writeString(orderedProductId);
        parcel.writeString(sellerEmail);
        parcel.writeString(orderedProductName);
        parcel.writeString(orderedProductPrice);
        parcel.writeString(productImage);
        parcel.writeString(shippingAddress);
        parcel.writeString(shippingName);
        parcel.writeString(shippingNumber);
        parcel.writeString(totalPrice);
    }

    @Override
    public String toString() {
        return "Order{" +
                ",orderStatus='" + orderStatus + '\'' +
                ",orderId='" + orderId + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", billingEmail='" + billingEmail + '\'' +
                ", billingName='" + billingName + '\'' +
                ", billingNumber='" + billingNumber + '\'' +
                ", currentDate='" + currentDate + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", orderedBuyerEmail='" + orderedBuyerEmail + '\'' +
                ", orderedProductId='" + orderedProductId + '\'' +
                ", sellerEmail='" + sellerEmail + '\'' +
                ", orderedProductName='" + orderedProductName + '\'' +
                ", orderedProductPrice='" + orderedProductPrice + '\'' +
                ", productImage='" + productImage + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", shippingName='" + shippingName + '\'' +
                ", shippingNumber='" + shippingNumber + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }
}
