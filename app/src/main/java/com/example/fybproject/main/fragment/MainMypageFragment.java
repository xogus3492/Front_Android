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

import com.bumptech.glide.Glide;
import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.infoDTO.InfoDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.service.InfoService;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMypageFragment extends Fragment {
    View view;

    TextView updateBtn, myClosetBtn
            , nameView, genderView, ageView, heightView, weightView;
    CircleImageView imgView;

    MainActivity mainactivity;

    private Context context;
    private InfoService infoService;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainactivity = (MainActivity)getActivity();
        this.context = context;
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
        imgView = view.findViewById(R.id.mypageImg);
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

                                // 이미지 불러오기
                                if(data.get(0).getProfileImagePath() != null) {
                                    String imageUrl = data.get(0).getProfileImagePath();
                                    Glide.with(context).load(imageUrl).into(imgView);
                                }

                                nameView.setText(data.get(0).getName());

                                if(data.get(0).getGender().equals("M"))
                                    genderView.setText("남자");
                                if(data.get(0).getGender().equals("W"))
                                    genderView.setText("여자");

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
