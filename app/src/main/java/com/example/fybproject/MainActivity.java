package com.example.fybproject;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fybproject.main.fragment.MainCartFragment;
import com.example.fybproject.main.fragment.MainCartUpdateFragment;
import com.example.fybproject.main.fragment.MainChangePwFragment;
import com.example.fybproject.main.fragment.MainHomeFragment;
import com.example.fybproject.main.fragment.MainMyclosetFragment;
import com.example.fybproject.main.fragment.MainMyclosetUpdateFragment;
import com.example.fybproject.main.fragment.MainMypageFragment;
import com.example.fybproject.main.fragment.MainMypageUpdateFragment;
import com.example.fybproject.main.fragment.MainSearchFragment;
import com.example.fybproject.main.fragment.MainSettingsFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ImageView navBar, hideBtn;
    Button homeBtn, searchBtn, modelBtn, cartBtn, mypageBtn;
    LinearLayout hideBtnWrapper;

    MainHomeFragment mainHomeFragment;
    MainSearchFragment mainSearchFragment;
    MainCartFragment mainCartFragment;
    MainCartUpdateFragment mainCartUpdateFragment;
    MainMypageFragment mainMypageFragment;
    MainMypageUpdateFragment mainMypageUpdateFragment;
    MainMyclosetFragment mainMyclosetFragment;
    MainMyclosetUpdateFragment mainMyclosetUpdateFragment;
    MainSettingsFragment mainSettingsFragment;
    MainChangePwFragment mainChangePwFragment;

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
        hideBtn = findViewById(R.id.hideBtn);
        hideBtnWrapper = findViewById(R.id.hideBtnWrapper);

        mainHomeFragment = new MainHomeFragment();
        mainSearchFragment = new MainSearchFragment();
        mainCartFragment = new MainCartFragment();
        mainCartUpdateFragment = new MainCartUpdateFragment();
        mainMypageFragment = new MainMypageFragment();
        mainMypageUpdateFragment = new MainMypageUpdateFragment();
        mainMyclosetFragment = new MainMyclosetFragment();
        mainMyclosetUpdateFragment = new MainMyclosetUpdateFragment();
        mainSettingsFragment = new MainSettingsFragment();
        mainChangePwFragment = new MainChangePwFragment();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.frameMain, mainHomeFragment).commitAllowingStateLoss();

        homeBtn.setOnClickListener(navListener);
        searchBtn.setOnClickListener(navListener);
        modelBtn.setOnClickListener(navListener);
        cartBtn.setOnClickListener(navListener);
        mypageBtn.setOnClickListener(navListener);

        hideBtnWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();

                Log.i(TAG, "로그 찍혔어욤");
                transaction.replace(R.id.frameMain, mainSettingsFragment).addToBackStack(null).commitAllowingStateLoss();
                hideBtn.setImageResource(R.drawable.back_icon);
            }
        });
    }

    View.OnClickListener navListener = new View.OnClickListener() {
        @SuppressLint("ResourceType")
        @Override
        public void onClick(View view) {
            transaction = fragmentManager.beginTransaction();

            switch (view.getId())
            {
                case R.id.homeBtn:
                    navBar.setImageResource(R.drawable.home_navbar);
                    hideBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainHomeFragment).commitAllowingStateLoss();
                    break;
                case R.id.searchBtn:
                    navBar.setImageResource(R.drawable.search_navbar);
                    hideBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainSearchFragment).commitAllowingStateLoss();
                    break;
                case R.id.modelBtn:
                    //hideBtn.setVisibility(View.GONE);
                    //navBar.setImageResource(R.drawable.home_navbar);
                    break;
                case R.id.cartBtn:
                    navBar.setImageResource(R.drawable.cart_navbar);
                    hideBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainCartFragment).commitAllowingStateLoss();
                    break;
                case R.id.myPageBtn:
                    navBar.setImageResource(R.drawable.mypage_navbar);
                    hideBtn.setVisibility(View.VISIBLE);
                    transaction.replace(R.id.frameMain, mainMypageFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                /*case R.id.hideBtnWrapper:
                        Log.i(TAG, "로그 찍혔어욤");
                        transaction.replace(R.id.frameMain, mainSettingsFragment).addToBackStack(null).commitAllowingStateLoss();
                        hideBtn.setImageResource(R.drawable.back_icon);
                        break;*/
            }
        }
    }; // 네비게이션 바 클릭 리스너

    // 프래그먼트 내에서 다른 프래그먼트에 대한 작업

    public void changeToCartUpdateFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainCartUpdateFragment).commitAllowingStateLoss();
    } // 장바구니 수정 버튼

    public void changeToCartFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainCartFragment).commitAllowingStateLoss();
    } // 장바구니 확인 버튼

    public void changeToMypageUpdateFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainMypageUpdateFragment).addToBackStack(null).commitAllowingStateLoss();
    } // 프로필 수정 버튼

    public void changeToMypageFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.remove(mainMypageUpdateFragment).commitAllowingStateLoss();  // !!
        transaction.replace(R.id.frameMain, mainMypageFragment).commitAllowingStateLoss();
    } // 프로필 변경 버튼

    public void porofileUpdateCancel() {
        getSupportFragmentManager().beginTransaction().remove(mainMypageUpdateFragment).commitAllowingStateLoss();
        getSupportFragmentManager().popBackStack();
    } // 프로필 취소 버튼

    public void changeToMyclosetUpdateFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainMyclosetUpdateFragment).commitAllowingStateLoss();
    } // 내 옷장 수정 버튼

    public void changeToMyclosetFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainMyclosetFragment).commitAllowingStateLoss();
    } // 내 옷장 확인 버튼

    public void changeToChangePwFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainChangePwFragment).commitAllowingStateLoss();
    } // 비밀번호 변경 버튼
}