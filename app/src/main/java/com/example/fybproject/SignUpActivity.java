package com.example.fybproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fybproject.mediator.registerDataMediator;
import com.example.fybproject.pager.adapter.SignUpPagerAdapter;

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
                intent.putExtra("email", registerDataMediator.getEmail());
                intent.putExtra("pw", registerDataMediator.getPw());
                intent.putExtra("name", registerDataMediator.getName());
                intent.putExtra("gender", registerDataMediator.getGender());
                intent.putExtra("age", registerDataMediator.getAge());
                intent.putExtra("height", registerDataMediator.getHeight());
                intent.putExtra("weight", registerDataMediator.getWeight());
                intent.putExtra("form", registerDataMediator.getForm());
                intent.putExtra("sholder", registerDataMediator.getSholder());
                intent.putExtra("pelvis", registerDataMediator.getPelvis());
                intent.putExtra("leg", registerDataMediator.getLeg());
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