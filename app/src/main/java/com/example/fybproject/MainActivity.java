package com.example.fybproject;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fybproject.client.Client;
import com.example.fybproject.dto.LoginDTO;
import com.example.fybproject.dto.RegisterDTO;
import com.example.fybproject.service.AuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    TextView edit_email, edit_password, login_btn, go_register_btn;

    private  static final int REQUEST_CODE = 1000;

    String email, pw;

    private AuthService authService;
    private LoginDTO loginDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                loginDTO = new LoginDTO(email, pw);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                // 데이터 post
                Client client = Client.getInstance(1);

                if (client != null) {
                    authService = Client.getAuthService();
                    authService.postLoginData(loginDTO)
                            .enqueue(new Callback<LoginDTO>() {
                                @Override
                                public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                                    LoginDTO data = response.body();
                                    if(response.isSuccessful() && data.getStatus() == 200) {
                                        Log.d(TAG, "onResponse: 성공, 결과\n" + data.toString());
                                        Toast.makeText(getApplicationContext(), data.getStatusMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    if(!response.isSuccessful() && data.getStatus() == 500) {
                                        try {
                                            Log.d(TAG, "onResponse: 실패, response.body(): " + response.body() + ", 응답코드: " + response.code() + ", response.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getApplicationContext(), data.getStatusMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
                finish();
            }
        }); // 로그인 클릭

        go_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        }); // 회원가입 클릭
    }

    public void init() {
        edit_email = findViewById(R.id.editEmail1);
        edit_password = findViewById(R.id.editPassword1);
        login_btn = findViewById(R.id.loginBtn);
        go_register_btn = findViewById(R.id.goRegisterBtn);
    } // 컴포넌트 인스턴스화

    public void inputData() {
        email = edit_email.getText().toString();
        pw = edit_password.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

        }
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "취소되었습니다.", Toast.LENGTH_LONG).show();
        }
    } // 콜백 함수 결과
}