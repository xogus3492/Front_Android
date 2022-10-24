package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.infoDTO.EditDTO;
import com.example.fybproject.dto.infoDTO.InfoDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.service.InfoService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMypageUpdateFragment extends Fragment {
    View view;

    TextView okBtn ,cancelBtn;
    EditText updateEmail, updateName, updateGender, updateAge, updateHeight, updateWeight;

    String email, name, gender;
    int age, height, weight;

    MainActivity mainactivity;

    private InfoService infoService;

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
        view = inflater.inflate(R.layout.fragment_main_mypage_update, container, false);

        init();
        loadUserInfo();

        updateEmail.setOnFocusChangeListener(editClickListener);
        updateName.setOnFocusChangeListener(editClickListener);
        updateGender.setOnFocusChangeListener(editClickListener);
        updateAge.setOnFocusChangeListener(editClickListener);
        updateHeight.setOnFocusChangeListener(editClickListener);
        updateWeight.setOnFocusChangeListener(editClickListener);

        okBtn.setOnClickListener(btnClickListener);
        cancelBtn.setOnClickListener(btnClickListener);

        return view;
    }

    public void init() {
        okBtn = view.findViewById(R.id.updateOkBtn);
        cancelBtn = view.findViewById(R.id.updateCancelBtn);
        updateEmail = view.findViewById(R.id.updateEmail);
        updateName = view.findViewById(R.id.updateName);
        updateGender = view.findViewById(R.id.updateGender);
        updateAge = view.findViewById(R.id.updateAge);
        updateHeight = view.findViewById(R.id.updateHeight);
        updateWeight = view.findViewById(R.id.updateWeight);
    }

    public void inputData() {
        email = updateEmail.getText().toString();
        name = updateName.getText().toString();
        gender = updateGender.getText().toString();

        if(gender.equals("남자") )
            gender = "M";
        else if (gender.equals("여자"))
            gender = "W";
        else {
            release();
            Toast.makeText(view.getContext().getApplicationContext(), "성별을 '남자' 또는 '여자'로 입력 해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        age = Integer.parseInt(updateAge.getText().toString());
        height = Integer.parseInt(updateHeight.getText().toString());
        weight = Integer.parseInt(updateWeight.getText().toString());
    }

    public void loadUserInfo() {
        infoService = ServiceGenerator.createService(InfoService.class, JwtToken.getToken());

        if (infoService != null) {
            infoService.getInfoData()
                    .enqueue(new Callback<ArrayList<InfoDTO>>() {
                        @Override
                        public void onResponse(Call<ArrayList<InfoDTO>> call, Response<ArrayList<InfoDTO>> response) {
                            ArrayList<InfoDTO> data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "getInfoData : 성공,\nresponseBody : " + data);
                                Log.d(TAG, "=====================================================================");

                                updateEmail.setText(data.get(0).getEmail());
                                updateName.setText(data.get(0).getName());
                                updateGender.setText("남자"); // data.get(0).getGender()
                                updateAge.setText(String.valueOf(data.get(0).getAge()));
                                updateHeight.setText(String.valueOf(data.get(0).getHeight()));
                                updateWeight.setText(String.valueOf(data.get(0).getWeight()));
                            } else {
                                try {
                                    Log.d(TAG, "getInfoData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<InfoDTO>> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.toString());
                        }
                    });
        }
    }// 마이페이지 조회

    View.OnFocusChangeListener editClickListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                switch(view.getId()) {
                    case R.id.updateEmail:
                        updateEmail.setText(null);
                        break;
                    case R.id.updateName:
                        updateName.setText(null);
                        break;
                    case R.id.updateGender:
                        updateGender.setText(null);
                        break;
                    case R.id.updateAge:
                        updateAge.setText(null);
                        break;
                    case R.id.updateHeight:
                        updateHeight.setText(null);
                        break;
                    case R.id.updateWeight:
                        updateWeight.setText(null);
                        break;
                }
            }
        }
    };

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.updateOkBtn:
                    inputData();
                    EditDTO editDTO = new EditDTO(email, name, gender, age, weight, height);
                    infoService = ServiceGenerator.createService(InfoService.class, JwtToken.getToken());

                    if (infoService != null) {
                        infoService.patchSearchData(editDTO)
                                .enqueue(new Callback<EditDTO>() {
                                    @Override
                                    public void onResponse(Call<EditDTO> call, Response<EditDTO> response) {
                                        EditDTO data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(TAG, "getUpdateData : 성공,\nresponseBody : " + data);
                                            Log.d(TAG, "=====================================================================");

                                            mainactivity.porofileUpdateCancel();

                                            //Toast.makeText(getContext().getApplicationContext(), "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            try {
                                                Log.d(TAG, "getUpdateData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<EditDTO> call, Throwable t) {
                                        Log.d(TAG, "onFailure: " + t.toString());
                                    }
                                });
                    } // 마이페이지 수정
                    break;
                case R.id.updateCancelBtn:
                    mainactivity.porofileUpdateCancel();
                    break;
            }
        }
    };

    public void release() {
        email = null;
        name = null;
        gender = null;
        age = 0;
        height = 0;
        weight = 0;
    }
}
