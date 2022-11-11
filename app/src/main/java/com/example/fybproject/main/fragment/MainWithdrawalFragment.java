package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

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
import com.example.fybproject.dto.authDTO.DeleteDTO;
import com.example.fybproject.dto.authDTO.PwChangeDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.service.AuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainWithdrawalFragment extends Fragment {
    View view;
    private JwtToken JwtToken;

    TextView doWithdrawalBtn;
    EditText inputPw;
    ImageView hidePw, showPw;

    String pw;

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
        view = inflater.inflate(R.layout.fragment_user_withdrawal, container, false);

        init();
        clickWithdrawalBtn();

        hidePw.setOnClickListener(eyeListener);
        showPw.setOnClickListener(eyeListener);
        // 눈 아이콘

        return view;
    }

    View.OnClickListener eyeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.hideWithdrawalPw:
                    inputPw.setInputType(InputType.TYPE_CLASS_TEXT);
                    hidePw.setVisibility(View.GONE);
                    showPw.setVisibility(View.VISIBLE);
                    break;
                case R.id.showWithdrawalPw:
                    inputPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPw.setVisibility(View.GONE);
                    hidePw.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    public void clickWithdrawalBtn() {
        doWithdrawalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                DeleteDTO deleteDTO = new DeleteDTO(pw);
                authService = ServiceGenerator.createService(AuthService.class, JwtToken.getToken());

                if (authService != null) {
                    authService.deleteUserData(deleteDTO)
                            .enqueue(new Callback<DeleteDTO>() {
                                @Override
                                public void onResponse(Call<DeleteDTO> call, Response<DeleteDTO> response) {
                                    DeleteDTO data = response.body();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "deleteUser : 성공,\nresponseBody : " + data);
                                        Log.d(TAG, "=====================================================================");
                                        Toast.makeText(view.getContext().getApplicationContext(), "회원탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
                                        mainactivity.goSplash();
                                    } else {
                                        try {
                                            Log.d(TAG, "deleteUser : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<DeleteDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                } // 마이페이지 조회
            }
        });
    }

    public void init() {
        doWithdrawalBtn = view.findViewById(R.id.doWithdrawalBtn);
        inputPw = view.findViewById(R.id.inputPwforWithdrawal);
        hidePw = view.findViewById(R.id.hideWithdrawalPw);
        showPw = view.findViewById(R.id.showWithdrawalPw);
    }

    public void inputData() {
        pw = inputPw.getText().toString();
    }
}
