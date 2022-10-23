package com.example.fybproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fybproject.main.fragment.MainCartFragment;
import com.example.fybproject.main.fragment.MainChangePwFragment;
import com.example.fybproject.main.fragment.MainHomeFragment;
import com.example.fybproject.main.fragment.MainModelFragment;
import com.example.fybproject.main.fragment.MainMyclosetFragment;
import com.example.fybproject.main.fragment.MainMypageFragment;
import com.example.fybproject.main.fragment.MainMypageUpdateFragment;
import com.example.fybproject.main.fragment.MainSearchFragment;
import com.example.fybproject.main.fragment.MainSettingsFragment;
import com.example.fybproject.main.fragment.MainWithdrawalFragment;

public class MainActivity extends AppCompatActivity {

    ImageView navBar, optionBtn, backBtn, backBtn2;
    Button homeBtn, searchBtn, modelBtn, cartBtn, mypageBtn;

    MainHomeFragment mainHomeFragment;
    MainSearchFragment mainSearchFragment;
    MainModelFragment mainModelFragment;
    MainCartFragment mainCartFragment;
    MainMypageFragment mainMypageFragment;
    MainMypageUpdateFragment mainMypageUpdateFragment;
    MainMyclosetFragment mainMyclosetFragment;
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
                    backBtn2.setVisibility(View.INVISIBLE);
                    backBtn.setVisibility(View.INVISIBLE);
                    optionBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainHomeFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.searchBtn:
                    navBar.setImageResource(R.drawable.search_navbar);
                    backBtn2.setVisibility(View.INVISIBLE);
                    backBtn.setVisibility(View.INVISIBLE);
                    optionBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainSearchFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.modelBtn:
                    navBar.setImageResource(R.drawable.model_navbar);
                    backBtn2.setVisibility(View.INVISIBLE);
                    backBtn.setVisibility(View.INVISIBLE);
                    optionBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainModelFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.cartBtn:
                    navBar.setImageResource(R.drawable.cart_navbar);
                    backBtn2.setVisibility(View.INVISIBLE);
                    backBtn.setVisibility(View.INVISIBLE);
                    optionBtn.setVisibility(View.INVISIBLE);
                    transaction.replace(R.id.frameMain, mainCartFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.myPageBtn:
                    navBar.setImageResource(R.drawable.mypage_navbar);
                    backBtn2.setVisibility(View.INVISIBLE);
                    backBtn.setVisibility(View.INVISIBLE);
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
        mainMypageFragment = new MainMypageFragment();
        mainMypageUpdateFragment = new MainMypageUpdateFragment();
        mainMyclosetFragment = new MainMyclosetFragment();
        mainSettingsFragment = new MainSettingsFragment();
        mainChangePwFragment = new MainChangePwFragment();
        mainWithdrawalFragment = new MainWithdrawalFragment();
    }

    // 프래그먼트 내에서 다른 프래그먼트에 대한 작업

    public void changeToMypageUpdateFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainMypageUpdateFragment).addToBackStack(null).commitAllowingStateLoss();
    } // 프로필 수정 버튼

    public void changeToMyClosetFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMain, mainMyclosetFragment).addToBackStack(null).commitAllowingStateLoss();
    } // 내 옷장 버튼

    public void porofileUpdateCancel() {
        getSupportFragmentManager().beginTransaction().remove(mainMypageUpdateFragment).commitAllowingStateLoss();
        getSupportFragmentManager().popBackStack();
    } // 프로필 취소 버튼

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

    public void goSplash() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    } // 스플래시 화면 이동

    public void moveToSettingsFragment() {
        getSupportFragmentManager().beginTransaction().remove(mainChangePwFragment).commitAllowingStateLoss();
        getSupportFragmentManager().popBackStack();
    } // 비밀번호 변경 완료 시
}