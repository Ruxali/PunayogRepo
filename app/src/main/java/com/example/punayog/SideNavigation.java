//package com.example.punayog;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.View;
//
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import com.google.android.material.navigation.NavigationView;
//
//public class SideNavigation extends AppCompatActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.side_navigation);
//        statusBarColor();
//
//
//    }
//
//    public void statusBarColor(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2,this.getTheme()));
//        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor2));
//        }
//    }
//
//
//}
