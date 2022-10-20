package com.example.fybproject;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fybproject.pager.adapter.SignUpPagerAdapter;
import com.example.fybproject.pager.fragment.SignUpFirstFragment;
import com.example.fybproject.pager.fragment.SignUpSecondFragment;
import com.example.fybproject.pager.fragment.SignUpThirdFragment;

import me.relex.circleindicator.CircleIndicator3;

public class SignUpActivity extends AppCompatActivity {

    ImageView verificationBtn;

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;
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
        }); // 뷰 페이저 이벤트

        verificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpVerificationActivity.class);
                intent.putExtra("email", DataMediator.getEmail());
                intent.putExtra("pw", DataMediator.getPw());
                intent.putExtra("name", DataMediator.getName());
                intent.putExtra("gender", DataMediator.getGender());
                intent.putExtra("age", DataMediator.getAge());
                intent.putExtra("height", DataMediator.getHeight());
                intent.putExtra("weight", DataMediator.getWeight());
                intent.putExtra("form", DataMediator.getForm());
                intent.putExtra("sholder", DataMediator.getSholder());
                intent.putExtra("pelvis", DataMediator.getPelvis());
                intent.putExtra("leg", DataMediator.getLeg());
                startActivity(intent);
            }
        }); // 전화번호 인증 버튼
    }

    public void init() {
        verificationBtn = findViewById(R.id.verificationBtn);
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
        viewPager.setOffscreenPageLimit(3); // 최대 프래그먼트 수
    }
}