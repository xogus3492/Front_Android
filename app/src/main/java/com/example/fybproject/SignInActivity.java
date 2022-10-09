package com.example.fybproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.LoginDTO;
import com.example.fybproject.dto.authDTO.SocialDTO;
import com.example.fybproject.service.AuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    ImageView localLoginBtn, kakaoLoginBtn, googleLoginBtn;

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        authService = ServiceGenerator.createService(AuthService.class);

        localLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalSignInActivity.class);
                startActivity(intent);
            }
        }); // 로컬 로그인

        kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (authService != null) {
                    authService.getKakaoData()
                            .enqueue(new Callback<SocialDTO>() {
                                @Override
                                public void onResponse(Call<SocialDTO> call, Response<SocialDTO> response) {
                                    SocialDTO data = response.body();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "KakaoLogin : 성공,\nresponse.body : " + data + ",\nresonse.header : " + response.headers());

                                        if (data.getStatus().equals("SOCIAL_REGISTER_STATUS_TRUE")) {
                                            Intent intent = new Intent(getApplicationContext(), SocialSignUpActivity.class);
                                            startActivity(intent);
                                        }
                                        if (data.getStatus().equals("LOGIN_STATUS_TRUE")) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        try {
                                            Log.d(TAG, "KakaoLogin : 실패,\nresponse.body() : " + response.body() + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<SocialDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 카카오 로그인

        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (authService != null) {
                    authService.getGoogleData()
                            .enqueue(new Callback<SocialDTO>() {
                                @Override
                                public void onResponse(Call<SocialDTO> call, Response<SocialDTO> response) {
                                    SocialDTO data = response.body();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "GoogleLogin : 성공,\nresponse.body : " + data);

                                        if (data.getStatus().equals("SOCIAL_REGISTER_STATUS_TRUE")) {
                                            Intent intent = new Intent(getApplicationContext(), SocialSignUpActivity.class);
                                            startActivity(intent);
                                        }
                                        if (data.getStatus().equals("LOGIN_STATUS_TRUE")) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        try {
                                            Log.d(TAG, "GoogleLogin : 실패,\nresponse.body() : " + response.body() + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<SocialDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 구글 로그인
    }

    public void init() {
        localLoginBtn = findViewById(R.id.localLoginBtn);
        kakaoLoginBtn = findViewById(R.id.kakaoLoginBtn);
        googleLoginBtn = findViewById(R.id.googleLoginBtn);
    }
}