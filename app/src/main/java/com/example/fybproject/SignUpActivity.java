package com.example.fybproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.fybproject.pager.adapter.SignUpPagerAdapter;

import me.relex.circleindicator.CircleIndicator3;

public class SignUpActivity extends AppCompatActivity {

    ImageView doSignupBtn;

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 2;
    private CircleIndicator3 indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
        pagerConfig();



        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    viewPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator.animatePageSelected(position % num_page);
            }
        });
    }

    public void init() {
        doSignupBtn = findViewById(R.id.doSignupBtn);
    }

    public void inputData() {
        /*age = Integer.parseInt(edit_age.getText().toString());

        if(male_btn.isChecked())
            gender = male_btn.getText().toString();
        else if(female_btn.isChecked())
            gender = male_btn.getText().toString();*/
    }

    public void pagerConfig() {
        // ViewPager2
        viewPager = findViewById(R.id.viewPager);
        // Adapter
        pagerAdapter = new SignUpPagerAdapter(this, num_page);
        viewPager.setAdapter(pagerAdapter);
        // Indicator
        indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        indicator.createIndicators(num_page, 0);
        //ViewPager Setting
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL); // 슬라이드 방향
        viewPager.setCurrentItem(0); // 시작 지점
        viewPager.setOffscreenPageLimit(2); // 최대 프래그먼트 수
    }
}