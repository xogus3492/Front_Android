package com.example.fybproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.CheckDTO;
import com.example.fybproject.service.AuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPwActivity extends AppCompatActivity {
    TextView receiveCodeBtn;
    EditText inputEmailForPw, inputPhone, inputCode;
    ImageView findPwBtn;

    String pnum, email, randNum, code;

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        init();

        receiveCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputPhoneData();
                CheckDTO checkDTO = new CheckDTO(pnum);
                authService = ServiceGenerator.createService(AuthService.class);

                if (authService != null) {
                    authService.postCheckData(checkDTO)
                            .enqueue(new Callback<CheckDTO>() {
                                @Override
                                public void onResponse(Call<CheckDTO> call, Response<CheckDTO> response) {
                                    CheckDTO data = response.body();
                                    if (response.isSuccessful() == true) {

                                        //if (data.getStatus().equals("PHONE_NUM_ERROR"))

                                        Log.d(TAG, "receiveCode : 성공,\nresponseBody : " + data);
                                        randNum = data.getRandNum();
                                    } else {
                                        try {
                                            Log.d(TAG, "receiveCode : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<CheckDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 인증번호 받기

        findPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();

                if(email != null && code != null) {
                    if( code == randNum) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "인증번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }); // 비밀번호 찾기 버튼
    }

    public void init() {
        inputEmailForPw = findViewById(R.id.inputEmailForPw);
        inputPhone = findViewById(R.id.inputPhone);
        inputCode = findViewById(R.id.inputCode);
        receiveCodeBtn = findViewById(R.id.receiveCodeBtn);
        findPwBtn = findViewById(R.id.findPwBtn);
    }

    public void inputPhoneData() {
        if (inputPhone.getText().toString() != null) {
            pnum = inputPhone.getText().toString();
        } else {
            Toast.makeText(getApplicationContext(), "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void inputData() {
        if (inputEmailForPw.getText().toString() != null) {
            email = inputEmailForPw.getText().toString();
        } else {
            Toast.makeText(getApplicationContext(), "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if (inputCode.getText().toString() != null) {
            code = inputCode.getText().toString();
        } else {
            Toast.makeText(getApplicationContext(), "인증번호를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}