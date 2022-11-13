package com.example.fybproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.SocialLoginDTO;
import com.example.fybproject.dto.authDTO.SocialUrlDTO;
import com.example.fybproject.interceptor.JwtToken;
import com.example.fybproject.service.AuthService;

import java.io.IOException;

import okhttp3.Headers;
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

        localLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocalSignInActivity.class);
                startActivity(intent);
            }
        }); // 로컬 로그인 액티비티 이동

        kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authService = ServiceGenerator.createService(AuthService.class);

                if (authService != null) {
                    authService.getKakaoUrl()
                            .enqueue(new Callback<SocialUrlDTO>() {
                                @Override
                                public void onResponse(Call<SocialUrlDTO> call, Response<SocialUrlDTO> response) {
                                    SocialUrlDTO data = response.body();

                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "KakaoUrl : 성공,\nresponseBody : " + data);

                                        Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl()));
                                        startActivity(intentUrl);
                                    } else {
                                        try {
                                            Log.d(TAG, "KakaoUrl : 실패,\nresponseBody : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<SocialUrlDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 카카오 로그인 url 받기

        /*googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authService = ServiceGenerator.createService(AuthService.class);

                if (authService != null) {
                    authService.getGoogleData()
                            .enqueue(new Callback<SocialDTO>() {
                                @Override
                                public void onResponse(Call<SocialDTO> call, Response<SocialDTO> response) {
                                    SocialDTO data = response.body();
                                    Headers header = response.headers();

                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "GoogleLogin : 성공,\nresponseBody : " + data + ",\njwtToken : " + header.get("Authorization"));

                                        if (data.getStatus().equals("SOCIAL_REGISTER_STATUS_TRUE")) {
                                            JwtToken.setToken(header.get("Authorization"));

                                            Intent intent = new Intent(getApplicationContext(), SocialSignUpActivity.class);
                                            startActivity(intent);
                                        }
                                        if (data.getStatus().equals("LOGIN_STATUS_TRUE")) {
                                            JwtToken.setToken(header.get("Authorization"));

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        try {
                                            Log.d(TAG, "GoogleLogin : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
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
        }); // 구글 로그인*/
    }

    /*public void kakaoLogin(String url) {
        oauthService = ServiceGenerator.createService(AuthService.class);

        if (oauthService != null) {
            oauthService.getKakaoLogin(url)
                    .enqueue(new Callback<SocialLoginDTO>() {
                        @Override
                        public void onResponse(Call<SocialLoginDTO> call, Response<SocialLoginDTO> response) {
                            SocialLoginDTO data = response.body();
                            Headers header = response.headers();

                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "KakaoLogin : 성공,\nresponseBody : " + data + ",\njwtToken : " + header.get("Authorization"));

                                        if (data.getStatus().equals("SOCIAL_REGISTER_STATUS_TRUE")) {
                                            JwtToken.setToken(header.get("Authorization"));

                                            Intent intent = new Intent(getApplicationContext(), SocialSignUpActivity.class);
                                            startActivity(intent);
                                        }
                                        if (data.getStatus().equals("LOGIN_STATUS_TRUE")) {
                                            JwtToken.setToken(header.get("Authorization"));

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                            } else {
                                try {
                                    Log.d(TAG, "KakaoLogin : 실패,\nresponseBody : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<SocialLoginDTO> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.toString());
                        }
                    });
        }
    }*/

    public void init() {
        localLoginBtn = findViewById(R.id.localLoginBtn);
        kakaoLoginBtn = findViewById(R.id.kakaoLoginBtn);
        googleLoginBtn = findViewById(R.id.googleLoginBtn);
    }
}