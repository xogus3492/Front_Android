package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.LogoutDTO;
import com.example.fybproject.interceptor.JwtToken;
import com.example.fybproject.service.AuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSettingsFragment extends Fragment {
    View view;

    TextView changePwBtn, logoutBtn, withdrawalBtn;

    MainActivity mainactivity;

    private AuthService authService;

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
        view =  inflater.inflate(R.layout.fragment_mypage_settings, container, false);

        init();

        changePwBtn.setOnClickListener(listener);
        logoutBtn.setOnClickListener(listener);
        withdrawalBtn.setOnClickListener(listener);

        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.changePwBtn:
                    mainactivity.changeToChangePwFragment();
                    break;
                case R.id.withdrawalBtn:
                    mainactivity.changeToWithdrawalFragment();
                    break;
                case R.id.logoutBtn:
                    Log.d(TAG, "로그아웃 토큰: " + JwtToken.getToken());

                    LogoutDTO logoutDTO = new LogoutDTO("Bearer " + JwtToken.getToken());
                    authService = ServiceGenerator.createService(AuthService.class, JwtToken.getToken());

                    if (authService != null) {
                        authService.deleteLogoutData(logoutDTO)
                                .enqueue(new Callback<LogoutDTO>() {
                                    @Override
                                    public void onResponse(Call<LogoutDTO> call, Response<LogoutDTO> response) {
                                        LogoutDTO data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(TAG, "logout : 성공,\nresponseBody : " + data);
                                            Log.d(TAG, "=====================================================================");
                                            // JwtToken.setToken(null);
                                            Toast.makeText(view.getContext().getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                                            mainactivity.logout();
                                        } else {
                                            try {
                                                Log.d(TAG, "getInfoData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<LogoutDTO> call, Throwable t) {
                                        Log.d(TAG, "onFailure: " + t.toString());
                                    }
                                });
                    } // 마이페이지 조회
                    break;
            }
        }
    };

    public void init() {
        changePwBtn = view.findViewById(R.id.changePwBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        withdrawalBtn = view.findViewById(R.id.withdrawalBtn);
    }
}
