package com.example.fybproject.main.fragment;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    ImageView circle, mInfo
            , img1, img2, img3;

    private RecyclerView rV;
    private ClothesListItemAdapter adapter;
    private ArrayList<ClothesListItem> arr;
    private Context context;

    MainActivity mainactivity;

    String gender, imgName, physical;
    Uri uri;
    char form, pelvis, shoulder, leg;

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

        mInfo.setOnClickListener(onClickListener);
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setTitle("이용 방법"); //제목
            dlg.setMessage("체험해 보고 싶으신 옷을 선택하여 체험해 보세요.\n사이트 이동 이미지를 누르시면 선택하신 옷과 관련된 사이트로 이동합니다."); // 메시지
            //dlg.setIcon(R.drawable.deum); // 아이콘 설정
            // 확인 버튼
            dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dlg.show();
        }
    }; // 다이얼로그

    View.OnClickListener intentListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/clovis_kr/"));
            context.startActivity(intentUrl);
        }
    }; // 관련상품 이동

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
        mInfo = view.findViewById(R.id.mInfo);
        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
    }

    public void createList() {
        int i = 0, j = 0; // 옷 갯수

        if(gender.equals("M")) {
            i = 1;
            j = 6;

            //남자일 때 썸네일
            uri = Uri.parse("https://chw-bucket.s3.ap-northeast-2.amazonaws.com/model/" + "TT4" + "_"+ physical + ".mp4");
            Log.d(TAG, "경로 : " + uri);
            modeling.setVideoURI(uri);
            modeling.seekTo( 1 );

            // 남자 추천 이미지 썸네일
            String imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/images/TT4-1.jpg";
            Glide.with(context).load(imageUrl).into(img1);
            imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/images/TT4-2.jpg";
            Glide.with(context).load(imageUrl).into(img2);
            imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/images/TT4-3.jpg";
            Glide.with(context).load(imageUrl).into(img3);
        }
        if(gender.equals("W")) {
            i = 7;
            j = 15;

            // 여자일 때 썸네일
            uri = Uri.parse("https://chw-bucket.s3.ap-northeast-2.amazonaws.com/model/" + "WTC1" + "_"+ physical + ".mp4");
            Log.d(TAG, "경로 : " + uri);
            modeling.setVideoURI(uri);
            modeling.seekTo( 1 );

            // 여자 추천 이미지 썸네일
            String imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/images/WTC1-1.jpg";
            Glide.with(context).load(imageUrl).into(img1);
            imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/images/WTC1-2.jpg";
            Glide.with(context).load(imageUrl).into(img2);
            imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/images/WTC1-3.jpg";
            Glide.with(context).load(imageUrl).into(img3);
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
        modeling.seekTo( 1 );// 비디오 불러오기

        String imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/images/" + imgName + "-1.jpg";
        Glide.with(context).load(imageUrl).into(img1);
        imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/images/" + imgName + "-2.jpg";
        Glide.with(context).load(imageUrl).into(img2);
        imageUrl = "https://chw-bucket.s3.ap-northeast-2.amazonaws.com/images/" + imgName + "-3.jpg";
        Glide.with(context).load(imageUrl).into(img3);
    }
}
