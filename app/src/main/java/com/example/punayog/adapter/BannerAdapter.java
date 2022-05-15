package com.example.punayog.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.punayog.R;
import com.example.punayog.model.Banner;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private List<Banner> bannerList;

    public BannerAdapter(List<Banner> bannerList){
        this.bannerList = bannerList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout,container,false);
        ConstraintLayout bannerContainer = view.findViewById(R.id.bannerContainer);
        bannerContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(bannerList.get(position).getBackgroundColor())));
        ImageView banner = view.findViewById(R.id.bannerImageView);
        Picasso.get().load(bannerList.get(position).getBanner()).into(banner);
        container.addView(view,0);
        return view;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
