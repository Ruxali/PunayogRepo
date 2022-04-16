package com.example.punayog;

public class Upload {
    private String mImageUrl,productName,price,shortDesc,longDesc,location;
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

    public Upload(String mImageUrl, String productName, String price, String shortDesc, String longDesc, String location)
    {
        this.mImageUrl=mImageUrl;
        this.productName=productName;
        this.price=price;
        this.shortDesc=shortDesc;
        this.longDesc=longDesc;
        this.location=location;
    }


}
