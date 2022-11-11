package com.example.fybproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.PwChangeDTO;
import com.example.fybproject.service.AuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPwNewActivity extends AppCompatActivity {

    EditText inputNewPw, inputNewPwCheck;

    ImageView hideNewPw, showNewPw, hideNewPwCheck, showNewPwCheck, changePwBtn;

    String email, newPw;

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw_new);

        init();

        email = getIntent().getStringExtra("email");

        changePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                PwChangeDTO pwChangeDTO = new PwChangeDTO(email, newPw);
                authService = ServiceGenerator.createService(AuthService.class);

                if (authService != null) {
                    authService.putLostPasswordData(pwChangeDTO)
                            .enqueue(new Callback<PwChangeDTO>() {
                                @Override
                                public void onResponse(Call<PwChangeDTO> call, Response<PwChangeDTO> response) {
                                    PwChangeDTO data = response.body();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "changePw : 성공,\nresponseBody : " + data);

                                        if (data.getStatus().equals("PASSWORD_CHANGE_STATUS_TRUE")) {
                                            Intent intent = new Intent(getApplicationContext(), LocalSignInActivity.class);
                                            startActivity(intent);
                                        } // 성공
                                        if (data.getStatus().equals("NOT_FOUND_USER")) {
                                            Toast.makeText(getApplicationContext(), "해당 이메일의 유저가 존재하지 않습니다", Toast.LENGTH_LONG).show();
                                        }
                                        if (data.getStatus().equals("PASSWORD_IS_NOT_CHANGE")) {
                                            Toast.makeText(getApplicationContext(), "입력한 비밀번호로 변경할 수 없습니다", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        try {
                                            Log.d(TAG, "changePw : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<PwChangeDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 인증번호 받기

        hideNewPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputNewPw.setInputType(InputType.TYPE_CLASS_TEXT);
                hideNewPw.setVisibility(View.GONE);
                showNewPw.setVisibility(View.VISIBLE);
            }
        }); // newPw 보이기

        showNewPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputNewPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showNewPw.setVisibility(View.GONE);
                hideNewPw.setVisibility(View.VISIBLE);
            }
        }); // newPw 숨기기

        hideNewPwCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputNewPwCheck.setInputType(InputType.TYPE_CLASS_TEXT);
                hideNewPwCheck.setVisibility(View.GONE);
                showNewPwCheck.setVisibility(View.VISIBLE);
            }
        }); // newPwCheck 보이기

        showNewPwCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputNewPwCheck.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showNewPwCheck.setVisibility(View.GONE);
                hideNewPwCheck.setVisibility(View.VISIBLE);
            }
        }); // newPwCheck 숨기기
    }

    public void init() {
        inputNewPw = findViewById(R.id.inputNewPw);
        inputNewPwCheck = findViewById(R.id.inputNewPwCheck);
        hideNewPw = findViewById(R.id.hideNewPw);
        showNewPw = findViewById(R.id.showNewPw);
        hideNewPwCheck = findViewById(R.id.hideNewPwCheck);
        showNewPwCheck = findViewById(R.id.showNewPwCheck);
        changePwBtn = findViewById(R.id.changePwBtn);
    }

    public void inputData() {
        if (inputNewPw.getText().toString() == null) {
            Toast.makeText(getApplicationContext(), "새 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (inputNewPwCheck.getText().toString() == null) {
            Toast.makeText(getApplicationContext(), "새 비밀번호 확인을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (inputNewPw.getText().toString() != inputNewPwCheck.getText().toString()) {
            Toast.makeText(getApplicationContext(), "변경할 비밀번호가 맞는지 확인해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        newPw = inputNewPw.getText().toString();
    }
}
