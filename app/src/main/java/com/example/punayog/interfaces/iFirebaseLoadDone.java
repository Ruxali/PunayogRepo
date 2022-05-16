package com.example.punayog.interfaces;

import com.example.punayog.model.Banner;

import java.util.ArrayList;

public interface iFirebaseLoadDone {
    void onFirebaseLoadSuccess(ArrayList<Banner> bannerList);
    void onFirebaseLoadFailed(String message);
}
