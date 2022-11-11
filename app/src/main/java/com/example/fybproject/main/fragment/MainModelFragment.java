package com.example.fybproject.main.fragment;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.PhysicalDTO;
import com.example.fybproject.dto.infoDTO.InfoDTO;
import com.example.fybproject.interceptor.JwtToken;
import com.example.fybproject.listView.OnItemClick;
import com.example.fybproject.listView.modeling.ClothesListItem;
import com.example.fybproject.listView.modeling.ClothesListItemAdapter;
import com.example.fybproject.service.AuthService;
import com.example.fybproject.service.InfoService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModelFragment extends Fragment implements OnItemClick {
    View view;

    TextView mHeight, mWeight, mForm, mShoulder, mPelvis, mLeg;
    VideoView modeling;
    ImageView circle;

    private RecyclerView rV;
    private ClothesListItemAdapter adapter;
    private ArrayList<ClothesListItem> arr;
    private Context context;

    MainActivity mainactivity;

    String gender, imgName, physical;
    Uri uri;
    char form, pelvis, shoulder, leg;
    int height, weight;

    private AuthService authService;
    private InfoService infoService;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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
        view =  inflater.inflate(R.layout.fragment_main_model, container, false);

        init();
        getPysicalData();
        getUserInfo();

        modeling.setOnErrorListener(errorListener);

        circle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        modeling.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        modeling.pause();
                        break;
                }
                return true;
            }
        }); // 회전 아이콘

        return view;
    }

    MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            modeling.setVideoURI(uri);
            modeling.seekTo(1);
            modeling.start();
            return true;
        }
    }; // 미디어플레이어 에러 이벤트

    /*MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Uri uri = Uri.parse("https://chw-bucket.s3.ap-northeast-2.amazonaws.com/model/" + imgName + "_"+ physical + ".mp4");
            Log.d(TAG, "경로 : " + uri);
            modeling.setVideoURI(uri);
            modeling.seekTo( 1 );
            modeling.start();
        }
    }; // 영상 재생 다시시작*/

    public void init() {
        modeling = view.findViewById(R.id.videoView);
        circle = view.findViewById(R.id.circle);
        rV = view.findViewById(R.id.tRecyclerView);
        mHeight = view.findViewById(R.id.mHeight);
        mWeight = view.findViewById(R.id.mWeight);
        mForm = view.findViewById(R.id.mForm);
        mShoulder = view.findViewById(R.id.mShoulder);
        mPelvis = view.findViewById(R.id.mPelvis);
        mLeg = view.findViewById(R.id.mLeg);
    }

    public void createList() {
        int i = 0, j = 0;

        if(gender.equals("M")) {
            i = 1;
            j = 6;
        }
        if(gender.equals("W")) {
            i = 7;
            j = 15;
        }

        adapter = new ClothesListItemAdapter(this);
        rV.setAdapter(adapter);
        rV.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        arr = new ArrayList<>();
        for (; i <= j; i++) {
            arr.add(new ClothesListItem(i));
        }
        adapter.setList(arr);
    } // 리사이클러뷰 생성

    public void getUserInfo() {
        infoService = ServiceGenerator.createService(InfoService.class, JwtToken.getToken());

        if (infoService != null) {
            infoService.getInfoData()
                    .enqueue(new Callback<ArrayList<InfoDTO>>() {
                        @Override
                        public void onResponse(Call<ArrayList<InfoDTO>> call, Response<ArrayList<InfoDTO>> response) {
                            ArrayList<InfoDTO> data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(ContentValues.TAG, "getInfoData : 성공,\nresponseBody : " + data);
                                Log.d(ContentValues.TAG, "=====================================================================");
                                gender = data.get(0).getGender();
                                createList();

                                //nameView.setText(data.get(0).getName());

                                mHeight.setText(String.valueOf(data.get(0).getHeight()));
                                mWeight.setText(String.valueOf(data.get(0).getWeight()));
                            } else {
                                try {
                                    Log.d(ContentValues.TAG, "getInfoData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<InfoDTO>> call, Throwable t) {
                            Log.d(ContentValues.TAG, "onFailure: " + t.toString());
                        }
                    });
        }
    } // 유저 정보 조회

    public void getPysicalData() {
        authService = ServiceGenerator.createService(AuthService.class, JwtToken.getToken());

        if (authService != null) {
            authService.getPhysicalData()
                    .enqueue(new Callback<PhysicalDTO>() {
                        @Override
                        public void onResponse(Call<PhysicalDTO> call, Response<PhysicalDTO> response) {
                            PhysicalDTO data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(ContentValues.TAG, "getPhysicalData : 성공,\nresponseBody : " + data);
                                Log.d(ContentValues.TAG, "=====================================================================");

                                physical = data.getUserData();
                                form = physical.charAt(2);
                                pelvis = physical.charAt(3);
                                shoulder = physical.charAt(4);
                                leg = physical.charAt(5);

                                if(form == 1)
                                    mForm.setText("상체 비만");
                                if(form == 1)
                                    mForm.setText("하체 비만");
                                if(form == 1)
                                    mForm.setText("정상 체형");
                                if(shoulder == 1)
                                    mShoulder.setText("상");
                                if(shoulder == 1)
                                    mShoulder.setText("중");
                                if(shoulder == 1)
                                    mShoulder.setText("하");
                                if(pelvis == 1)
                                    mPelvis.setText("중상");
                                if(pelvis == 1)
                                    mPelvis.setText("중하");
                                if(leg == 1)
                                    mLeg.setText("중상");
                                if(leg== 1)
                                    mLeg.setText("중하");


                            } else {
                                try {
                                    Log.d(ContentValues.TAG, "getPhysicalData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<PhysicalDTO> call, Throwable t) {
                            Log.d(ContentValues.TAG, "onFailure: " + t.toString());
                        }
                    });
        }
    } // 유저 정보 조회

    @Override
    public void onClick(String imgName) {
        this.imgName = imgName;
        uri = Uri.parse("https://chw-bucket.s3.ap-northeast-2.amazonaws.com/model/" + imgName + "_"+ physical + ".mp4");
        Log.d(TAG, "경로 : " + uri);
        modeling.setVideoURI(uri);
        modeling.seekTo( 1 );
    }// 비디오 불러오기
}
