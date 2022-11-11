package com.example.fybproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

    TextView findPw;
    ImageView loginBtn, showPw, hidePw;
    EditText inputEmail, inputPw;
    private JwtToken JwtToken;

    String email, pw;

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_sign_in);

        init();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                LoginDTO loginDTO = new LoginDTO(email, pw);
                authService = ServiceGenerator.createService(AuthService.class);

                if (authService != null) {
                    authService.postLoginData(loginDTO)
                            .enqueue(new Callback<LoginDTO>() {
                                @Override
                                public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                                    LoginDTO data = response.body();
                                    Headers header = response.headers();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "LocalLogin : 성공,\nresponseBody : " + data + ",\njwtToken : " + header.get("Authorization"));
                                        Log.d(TAG, "=====================================================================");

                                        if (data.getStatus().equals("LOGIN_STATUS_TRUE")) {
                                            JwtToken.setToken(header.get("Authorization").substring(7));
                                            Toast.makeText(getApplicationContext(), "환영합니다.", Toast.LENGTH_LONG).show();

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }

                                    } else {
                                        try {
                                            Log.d(TAG, "LocalLogin : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            //if (data.getStatus().equals("LOGIN_FALSE")) {
                                                Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호를 다시 확인해 주십시오", Toast.LENGTH_LONG).show();
                                            //}
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
        }); // 로컬 로그인

        hidePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputPw.setInputType(InputType.TYPE_CLASS_TEXT);
                hidePw.setVisibility(View.GONE);
                showPw.setVisibility(View.VISIBLE);
            }
        }); // 패스워드 보이게

        showPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showPw.setVisibility(View.GONE);
                hidePw.setVisibility(View.VISIBLE);
            }
        }); // 패스워드 안 보이게

        findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindPwActivity.class);
                startActivity(intent);
            }
        }); // 비밀번호 찾기
    }

    public void init() {
        loginBtn = findViewById(R.id.loginBtn);
        inputEmail = findViewById(R.id.inputEmail);
        inputPw = findViewById(R.id.inputPw);
        showPw = findViewById(R.id.showPw);
        hidePw = findViewById(R.id.hidePw);
        findPw = findViewById(R.id.findPwText);
    }

    public void inputData() {
        email = inputEmail.getText().toString();
        pw = inputPw.getText().toString();
    }
}