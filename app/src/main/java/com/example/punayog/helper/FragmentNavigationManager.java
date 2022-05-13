package com.example.punayog.helper;

import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.punayog.BuildConfig;
import com.example.punayog.FragmentForSideNav;
import com.example.punayog.MainActivity;
import com.example.punayog.R;
import com.example.punayog.interfaces.NavigationManager;

public class FragmentNavigationManager implements NavigationManager {

    FragmentForSideNav fragment_for_side_nav = new FragmentForSideNav();

    private static FragmentNavigationManager mInstance;
    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;
    private FragmentForSideNav newInstance;

    public static FragmentNavigationManager getmInstance(MainActivity mainActivity){
        if(mInstance == null){
            mInstance = new FragmentNavigationManager();
        }
        mInstance.configure(mainActivity);
        return mInstance;
    }

    private void configure(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mFragmentManager = mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {

       showFragment(FragmentForSideNav.newInstance(title),false);
    }

    private void showFragment(FragmentForSideNav newInstance, boolean b) {

        FragmentManager fragmentManager = mFragmentManager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.container,fragment_for_side_nav);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }
}
