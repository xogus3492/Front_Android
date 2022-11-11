package com.example.fybproject;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fybproject.mediator.RegisterDataMediator;
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
                if(exception() == 1) return;

                Intent intent = new Intent(getApplicationContext(), SignUpVerificationActivity.class);
                intent.putExtra("email", RegisterDataMediator.getEmail());
                intent.putExtra("pw", RegisterDataMediator.getPw());
                intent.putExtra("name", RegisterDataMediator.getName());
                intent.putExtra("gender", RegisterDataMediator.getGender());
                intent.putExtra("age", Integer.parseInt(RegisterDataMediator.getAge()));
                intent.putExtra("height", Integer.parseInt(RegisterDataMediator.getHeight()));
                intent.putExtra("weight", Integer.parseInt(RegisterDataMediator.getWeight()));
                intent.putExtra("form", RegisterDataMediator.getForm());
                intent.putExtra("sholder", RegisterDataMediator.getSholder());
                intent.putExtra("pelvis", RegisterDataMediator.getPelvis());
                intent.putExtra("leg", RegisterDataMediator.getLeg());
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

    public int exception() {
        if (RegisterDataMediator.getEmail().equals("")) {
            Toast.makeText(getApplicationContext(), "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getPw().equals("")) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (!RegisterDataMediator.getPw().equals(RegisterDataMediator.getPwCheck())) {
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getName().equals("")) {
            Toast.makeText(getApplicationContext(), "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getGender().equals("")) {
            Toast.makeText(getApplicationContext(), "성별을 선택해 주세요", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getAge().equals("")) {
            Toast.makeText(getApplicationContext(), "나이를 입력해 주세요", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getHeight().equals("")) {
            Toast.makeText(getApplicationContext(), "키를 입력해 주세요", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getWeight().equals("")) {
            Toast.makeText(getApplicationContext(), "몸무게를 입력해 주세요", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getForm().equals("")) {
            Toast.makeText(getApplicationContext(), "체형을 선택해 주세요", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getSholder().equals("")) {
            Toast.makeText(getApplicationContext(), "어깨 너비를 선택해 주세요", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getPelvis().equals("")) {
            Toast.makeText(getApplicationContext(), "골반 너비을 선택해 주세요", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (RegisterDataMediator.getLeg().equals("")) {
            Toast.makeText(getApplicationContext(), "다리 두께를 선택해 주세요", Toast.LENGTH_SHORT).show();
            return 1;
        }
        return 0;
    } // 예외처리
}