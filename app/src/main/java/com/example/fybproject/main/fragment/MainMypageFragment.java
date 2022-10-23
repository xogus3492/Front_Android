package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.infoDTO.InfoDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.service.InfoService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMypageFragment extends Fragment {
    View view;

    TextView updateBtn, myClosetBtn
            , nameView, genderView, ageView, heightView, weightView;

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
        view =  inflater.inflate(R.layout.fragment_main_mypage, container, false);

        init();
        loadMypage();


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainactivity.changeToMypageUpdateFragment();
            }
        }); // 수정 버튼

        myClosetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainactivity.changeToMyClosetFragment();
            }
        });

        return view;
    }

    public void init() {
        updateBtn = view.findViewById(R.id.profileUpdateBtn);
        myClosetBtn = view.findViewById(R.id.myClosetBtn);
        nameView = view.findViewById(R.id.mypageName);
        genderView = view.findViewById(R.id.mypageGender);
        ageView = view.findViewById(R.id.mypageAge);
        heightView = view.findViewById(R.id.mypageHeight);
        weightView = view.findViewById(R.id.mypageWeight);
    }

    public void loadMypage() {
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

                                nameView.setText(data.get(0).getName());
                                genderView.setText(data.get(0).getGender());
                                ageView.setText(String.valueOf(data.get(0).getAge()));
                                heightView.setText(String.valueOf(data.get(0).getHeight()));
                                weightView.setText(String.valueOf(data.get(0).getWeight()));
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
        } // 마이페이지 조회
    }
}
