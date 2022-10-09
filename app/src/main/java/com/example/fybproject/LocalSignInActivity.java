package com.example.fybproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.LoginDTO;
import com.example.fybproject.dto.authDTO.SocialDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.service.AuthService;

import java.io.IOException;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalSignInActivity extends AppCompatActivity {

    ImageView loginBtn;
    EditText inputEmail, inputPw;

    String email, pw;

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_sign_in);

        init();

        authService = ServiceGenerator.createService(AuthService.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                LoginDTO loginDTO = new LoginDTO(email, pw);

                if (authService != null) {
                    authService.postLoginData(loginDTO)
                            .enqueue(new Callback<LoginDTO>() {
                                @Override
                                public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                                    LoginDTO data = response.body();
                                    Headers header = response.headers();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "KakaoLogin : 성공,\nresponse.body : " + data + ",\nresonse.header : " + response.headers());

                                        if (data.getStatus().equals("LOGIN_STATUS_TRUE")) {
                                            Log.d(TAG, "jwt token : " + header.get("Authorization"));
                                            JwtToken.setToken(header.get("Authorization"));

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                        if (data.getStatus().equals("LOGIN_FALSE")) {
                                            Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호를 다시 확인 해 주십시오", Toast.LENGTH_LONG).show();
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
                                public void onFailure(Call<LoginDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        });
    }

    public void init() {
        loginBtn = findViewById(R.id.loginBtn);
        inputEmail = findViewById(R.id.inputEmail);
        inputPw = findViewById(R.id.inputPw);
    }

    public void inputData() {
        email = inputEmail.getText().toString();
        pw = inputPw.getText().toString();
    }
}