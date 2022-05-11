package com.example.punayog;

public class SearchDeal {
    private String location,longDesc,image,price,productName,shortDesc;

    public SearchDeal() {
    }

    public SearchDeal(String location, String longDesc, String image, String price, String productName, String shortDesc) {
        this.location = location;
        this.longDesc = longDesc;
        this.image = image;
        this.price = price;
        this.productName = productName;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }


}
