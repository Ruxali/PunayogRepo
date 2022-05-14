package com.example.punayog.model;

import android.view.View;

public class User {
    public String inputUsername, inputDOB ,emailInput,phoneInput ,pswInput,pswTwoInput,addInput,userGender,mImageUrl;
    public User(){}

    public String getInputUsername() {
        return inputUsername;
    }

    public void setInputUsername(String inputUsername) {
        this.inputUsername = inputUsername;
    }

    public String getInputDOB() {
        return inputDOB;
    }

    public void setInputDOB(String inputDOB) {
        this.inputDOB = inputDOB;
    }

    public String getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(String emailInput) {
        this.emailInput = emailInput;
    }

    public String getPhoneInput() {
        return phoneInput;
    }

    public void setPhoneInput(String phoneInput) {
        this.phoneInput = phoneInput;
    }

    public String getPswInput() {
        return pswInput;
    }

    public void setPswInput(String pswInput) {
        this.pswInput = pswInput;
    }

    public String getPswTwoInput() {
        return pswTwoInput;
    }

    public void setPswTwoInput(String pswTwoInput) {
        this.pswTwoInput = pswTwoInput;
    }

    public String getAddInput() {
        return addInput;
    }

    public void setAddInput(String addInput) {
        this.addInput = addInput;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public User(String inputUsername, String inputDOB, String emailInput, String phoneInput, String pswInput, String pswTwoInput, String addInput, String userGender, String mImageUrl){
        this.inputUsername=inputUsername;
        this.inputDOB=inputDOB;
        this.emailInput=emailInput;
        this.phoneInput=phoneInput;
        this.pswInput=pswInput;
        this.pswTwoInput=pswTwoInput;
        this.addInput=addInput;
        this.userGender=userGender;
        this.mImageUrl=mImageUrl;

    }
}
