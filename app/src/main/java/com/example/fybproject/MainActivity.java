package com.example.fybproject;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.fybproject.main.fragment.MainCartFragment;
import com.example.fybproject.main.fragment.MainCartUpdateFragment;
import com.example.fybproject.main.fragment.MainChangePwFragment;
import com.example.fybproject.main.fragment.MainHomeFragment;
import com.example.fybproject.main.fragment.MainModelFragment;
import com.example.fybproject.main.fragment.MainMyclosetFragment;
import com.example.fybproject.main.fragment.MainMyclosetUpdateFragment;
import com.example.fybproject.main.fragment.MainMypageFragment;
import com.example.fybproject.main.fragment.MainMypageUpdateFragment;
import com.example.fybproject.main.fragment.MainSearchFragment;
import com.example.fybproject.main.fragment.MainSettingsFragment;
import com.example.fybproject.main.fragment.MainWithdrawalFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ImageView navBar, optionBtn, backBtn, backBtn2;
    Button homeBtn, searchBtn, modelBtn, cartBtn, mypageBtn;

    MainHomeFragment mainHomeFragment;
    MainSearchFragment mainSearchFragment;
    MainModelFragment mainModelFragment;
    MainCartFragment mainCartFragment;
    MainCartUpdateFragment mainCartUpdateFragment;
    MainMypageFragment mainMypageFragment;
    MainMypageUpdateFragment mainMypageUpdateFragment;
    MainMyclosetFragment mainMyclosetFragment;
    MainMyclosetUpdateFragment mainMyclosetUpdateFragment;
    MainSettingsFragment mainSettingsFragment;
    MainChangePwFragment mainChangePwFragment;
    MainWithdrawalFragment mainWithdrawalFragment;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.frameMain, mainHomeFragment).commitAllowingStateLoss();

        homeBtn.setOnClickListener(navListener);
        searchBtn.setOnClickListener(navListener);
        modelBtn.setOnClickListener(navListener);
        cartBtn.setOnClickListener(navListener);
        mypageBtn.setOnClickListener(navListener);

        optionBtn.setOnClickListener(navListener);
        backBtn.setOnClickListener(navListener);
        backBtn2.setOnClickListener(navListener);
        // 설정 버튼
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
                    optionBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainHomeFragment).commitAllowingStateLoss();
                    break;
                case R.id.searchBtn:
                    navBar.setImageResource(R.drawable.search_navbar);
                    optionBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainSearchFragment).commitAllowingStateLoss();
                    break;
                case R.id.modelBtn:
                    navBar.setImageResource(R.drawable.model_navbar);
                    optionBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainModelFragment).commitAllowingStateLoss();
                    break;
                /*case R.id.cartBtn:
                    navBar.setImageResource(R.drawable.cart_navbar);
                    optionBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainCartFragment).commitAllowingStateLoss();
                    break;*/
                case R.id.myPageBtn:
                    navBar.setImageResource(R.drawable.mypage_navbar);
                    optionBtn.setVisibility(View.VISIBLE);
                    transaction.replace(R.id.frameMain, mainMypageFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.optionBtn:
                    optionBtn.setVisibility(View.INVISIBLE);
                    backBtn.setVisibility(View.VISIBLE);
                    transaction.replace(R.id.frameMain, mainSettingsFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.backBtn:
                    optionBtn.setVisibility(View.VISIBLE);
                    backBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainMypageFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.backBtn2:
                    backBtn.setVisibility(View.VISIBLE);
                    backBtn2.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainSettingsFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
            }
        }
    }; // 네비게이션 바 클릭 리스너

    public void init() {
        navBar = findViewById(R.id.mainNavBar);
        homeBtn = findViewById(R.id.homeBtn);
        searchBtn = findViewById(R.id.searchBtn);
        modelBtn = findViewById(R.id.modelBtn);
        cartBtn = findViewById(R.id.cartBtn);
        mypageBtn = findViewById(R.id.myPageBtn);
        optionBtn = findViewById(R.id.optionBtn);
        backBtn = findViewById(R.id.backBtn);
        backBtn2 = findViewById(R.id.backBtn2);

        mainHomeFragment = new MainHomeFragment();
        mainSearchFragment = new MainSearchFragment();
        mainModelFragment = new MainModelFragment();
        mainCartFragment = new MainCartFragment();
        mainCartUpdateFragment = new MainCartUpdateFragment();
        mainMypageFragment = new MainMypageFragment();
        mainMypageUpdateFragment = new MainMypageUpdateFragment();
        mainMyclosetFragment = new MainMyclosetFragment();
        mainMyclosetUpdateFragment = new MainMyclosetUpdateFragment();
        mainSettingsFragment = new MainSettingsFragment();
        mainChangePwFragment = new MainChangePwFragment();
        mainWithdrawalFragment = new MainWithdrawalFragment();
    }

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
        backBtn2.setVisibility(View.VISIBLE);
        backBtn.setVisibility(View.INVISIBLE);
    } // 비밀번호 변경 버튼

    public void changeToWithdrawalFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainWithdrawalFragment).commitAllowingStateLoss();
        backBtn2.setVisibility(View.VISIBLE);
        backBtn.setVisibility(View.INVISIBLE);
    } // 회원 탈퇴 버튼

    public void goSplashBylogout() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    } // 로그아웃 스플래시 화면 이동
}