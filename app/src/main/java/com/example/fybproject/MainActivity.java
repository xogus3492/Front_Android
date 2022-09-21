package com.example.fybproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fybproject.main.fragment.MainCartFragment;
import com.example.fybproject.main.fragment.MainCartUpdateFragment;
import com.example.fybproject.main.fragment.MainHomeFragment;
import com.example.fybproject.main.fragment.MainMypageFragment;
import com.example.fybproject.main.fragment.MainMypageUpdateFragment;
import com.example.fybproject.main.fragment.MainSearchFragment;

public class MainActivity extends AppCompatActivity {

    ImageView navBar;
    Button homeBtn, searchBtn, modelBtn, cartBtn, mypageBtn;

    MainHomeFragment mainHomeFragment;
    MainSearchFragment mainSearchFragment;
    MainCartFragment mainCartFragment;
    MainCartUpdateFragment mainCartUpdateFragment;
    MainMypageFragment mainMypageFragment;
    MainMypageUpdateFragment mainMypageUpdateFragment;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navBar = findViewById(R.id.mainNavBar);
        homeBtn = findViewById(R.id.homeBtn);
        searchBtn = findViewById(R.id.searchBtn);
        modelBtn = findViewById(R.id.modelBtn);
        cartBtn = findViewById(R.id.cartBtn);
        mypageBtn = findViewById(R.id.myPageBtn);

        mainHomeFragment = new MainHomeFragment();
        mainSearchFragment = new MainSearchFragment();
        mainCartFragment = new MainCartFragment();
        mainCartUpdateFragment = new MainCartUpdateFragment();
        mainMypageFragment = new MainMypageFragment();
        mainMypageUpdateFragment = new MainMypageUpdateFragment();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.frameMain, mainHomeFragment).commitAllowingStateLoss();

        homeBtn.setOnClickListener(navListener);
        searchBtn.setOnClickListener(navListener);
        modelBtn.setOnClickListener(navListener);
        cartBtn.setOnClickListener(navListener);
        mypageBtn.setOnClickListener(navListener);
    }

    View.OnClickListener navListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transaction = fragmentManager.beginTransaction();

            switch (view.getId())
            {
                case R.id.homeBtn:
                    navBar.setImageResource(R.drawable.home_navbar);
                    transaction.replace(R.id.frameMain, mainHomeFragment).commitAllowingStateLoss();
                    break;
                case R.id.searchBtn:
                    navBar.setImageResource(R.drawable.search_navbar);
                    transaction.replace(R.id.frameMain, mainSearchFragment).commitAllowingStateLoss();
                    break;
                case R.id.modelBtn:
                    //navBar.setImageResource(R.drawable.home_navbar);
                    break;
                case R.id.cartBtn:
                    navBar.setImageResource(R.drawable.cart_navbar);
                    transaction.replace(R.id.frameMain, mainCartFragment).commitAllowingStateLoss();
                    break;
                case R.id.myPageBtn:
                    navBar.setImageResource(R.drawable.mypage_navbar);
                    transaction.replace(R.id.frameMain, mainMypageFragment).commitAllowingStateLoss();
                    break;
            }
        }
    }; // 네비게이션 바 클릭 리스너

    public void changeToCartUpdateFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainCartUpdateFragment).commitAllowingStateLoss();
    } // 장바구니 수정 버튼

    public void changeToMypageUpdateFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainMypageUpdateFragment).commitAllowingStateLoss();
    } // 장바구니 수정 버튼
}