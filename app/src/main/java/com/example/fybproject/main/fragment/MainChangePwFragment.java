package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.CheckDTO;
import com.example.fybproject.dto.authDTO.PwChangeDTO;
import com.example.fybproject.dto.infoDTO.InfoDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.service.AuthService;
import com.example.fybproject.service.InfoService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainChangePwFragment extends Fragment {
    View view;

    TextView ChangePwOkBtn, receiveCodeForChangePw;
    EditText emailForChangePw, pwForChangePw, newPwForChangePw, phoneForChangePw, codeForChangePw;
    ImageView hideChangePw, showChangePw, hideChangeNewPw, showChangeNewPw;

    String pnum, randNum, code
            ,email, pw, newPw;

    MainActivity mainactivity;

    private AuthService authService, authService1;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainactivity = (MainActivity)getActivity();
    } // 메인액티비티 객체 가져오기

    @Override
    public void onDetach() {
        super.onDetach();
        mainactivity=null;
    } // 메인액티비티 객체 해제

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_pw, container, false);

        init();
        receiveCode();
        changePw();

        hideChangePw.setOnClickListener(eyeListener);
        showChangePw.setOnClickListener(eyeListener);
        hideChangeNewPw.setOnClickListener(eyeListener);
        showChangeNewPw.setOnClickListener(eyeListener);
        // 눈 아이콘 클릭

        return view;
    }

    View.OnClickListener eyeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.hideChangePw:
                    pwForChangePw.setInputType(InputType.TYPE_CLASS_TEXT);
                    hideChangePw.setVisibility(View.GONE);
                    showChangePw.setVisibility(View.VISIBLE);
                    break;
                case R.id.showChangePw:
                    pwForChangePw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showChangePw.setVisibility(View.GONE);
                    hideChangePw.setVisibility(View.VISIBLE);
                    break;
                case R.id.hideChangeNewPw:
                    newPwForChangePw.setInputType(InputType.TYPE_CLASS_TEXT);
                    hideChangeNewPw.setVisibility(View.GONE);
                    showChangeNewPw.setVisibility(View.VISIBLE);
                    break;
                case R.id.showChangeNewPw:
                    newPwForChangePw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showChangeNewPw.setVisibility(View.GONE);
                    hideChangeNewPw.setVisibility(View.VISIBLE);
                    break;
            }

        }
    };

    public void receiveCode() {
        receiveCodeForChangePw.setOnClickListener(new View.OnClickListener() {
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
                                        //PHONE_CHECK_STATUS_TRUE
                                        Log.d(ContentValues.TAG, "receiveCode : 성공,\nresponseBody : " + data);

                                        Toast.makeText(getContext().getApplicationContext(), "인증번호를 발송하였습니다.", Toast.LENGTH_SHORT).show();
                                        randNum = data.getRandNum();
                                    } else {
                                        try {
                                            Log.d(ContentValues.TAG, "receiveCode : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<CheckDTO> call, Throwable t) {
                                    Log.d(ContentValues.TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 인증번호 받기
    }

    public void changePw() {
        ChangePwOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                PwChangeDTO pwChangeDTO = new PwChangeDTO(email, pw, newPw);
                authService1 = ServiceGenerator.createService(AuthService.class, JwtToken.getToken());

                if (authService1 != null) {
                    authService1.patchPasswordData(pwChangeDTO)
                            .enqueue(new Callback<PwChangeDTO>() {
                                @Override
                                public void onResponse(Call<PwChangeDTO> call, Response<PwChangeDTO> response) {
                                    PwChangeDTO data = response.body();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "pwChange : 성공,\nresponseBody : " + data);
                                        Log.d(TAG, "=====================================================================");
                                        Toast.makeText(view.getContext().getApplicationContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                        mainactivity.moveToSettingsFragment();
                                    } else {
                                        try {
                                            Log.d(TAG, "pwChange : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
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
                } // 마이페이지 조회
            }
        });
    }

    public void init() {
        ChangePwOkBtn =view.findViewById(R.id.ChangePwOkBtn);
        emailForChangePw = view.findViewById(R.id.emailForChangePw);
        pwForChangePw = view.findViewById(R.id.pwForChangePw);
        newPwForChangePw = view.findViewById(R.id.newPwForChangePw);
        phoneForChangePw = view.findViewById(R.id.phoneForChangePw);
        codeForChangePw = view.findViewById(R.id.codeForChangePw);
        receiveCodeForChangePw = view.findViewById(R.id.receiveCodeForChangePw);
        hideChangePw = view.findViewById(R.id.hideChangePw);
        showChangePw = view.findViewById(R.id.showChangePw);
        hideChangeNewPw = view.findViewById(R.id.hideChangeNewPw);
        showChangeNewPw = view.findViewById(R.id.showChangeNewPw);
    }

    public void inputData() {
        email = emailForChangePw.getText().toString();
        pw = pwForChangePw.getText().toString();
        newPw = newPwForChangePw.getText().toString();

        if (codeForChangePw.getText().toString() != null) {
            code = codeForChangePw.getText().toString();
        } else {
            Toast.makeText(view.getContext().getApplicationContext(), "인증번호를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void inputPhoneData() {
        if (phoneForChangePw.getText().toString() != null) {
            pnum = phoneForChangePw.getText().toString();
        } else {
            Toast.makeText(view.getContext().getApplicationContext(), "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
