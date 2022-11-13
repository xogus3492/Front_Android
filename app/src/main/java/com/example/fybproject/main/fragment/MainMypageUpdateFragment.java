package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.ProfileImgDTO;
import com.example.fybproject.dto.infoDTO.EditDTO;
import com.example.fybproject.dto.infoDTO.InfoDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetImgDTO;
import com.example.fybproject.interceptor.JwtToken;
import com.example.fybproject.service.AuthService;
import com.example.fybproject.service.InfoService;
import com.example.fybproject.service.MyClosetService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMypageUpdateFragment extends Fragment {
    View view;

    TextView okBtn ,cancelBtn;
    EditText updateEmail, updateName, updateGender, updateAge, updateHeight, updateWeight;
    CircleImageView imgView;
    Button button; // 임시용

    String email, name, gender;
    int age, height, weight;

    MainActivity mainactivity;

    private static final int REQUEST_CODE = 1001;
    private MultipartBody.Part body;

    private Context context;
    private InfoService infoService;
    private AuthService authService;

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
        view = inflater.inflate(R.layout.fragment_main_mypage_update, container, false);

        init();
        loadUserInfo();

        updateEmail.setOnFocusChangeListener(editClickListener);
        updateName.setOnFocusChangeListener(editClickListener);
        updateGender.setOnFocusChangeListener(editClickListener);
        updateAge.setOnFocusChangeListener(editClickListener);
        updateHeight.setOnFocusChangeListener(editClickListener);
        updateWeight.setOnFocusChangeListener(editClickListener);

        imgView.setOnClickListener(onClickListener);
        okBtn.setOnClickListener(onClickListener);
        cancelBtn.setOnClickListener(onClickListener);

        button.setOnClickListener(onClickListener); // 임시용

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
        imgView = view.findViewById(R.id.mypageUimg);

        button = view.findViewById(R.id.button); // 임시용
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
        body = null;

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

                                updateEmail.setText(data.get(0).getEmail());
                                updateName.setText(data.get(0).getName());

                                if(data.get(0).getGender().equals("M"))
                                    updateGender.setText("남자");
                                if(data.get(0).getGender().equals("W"))
                                    updateGender.setText("여자");

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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.mypageUimg:
                    //ProfileImageActivity.verifyStoragePermissions(getActivity());

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    //intent.setType("image/*");
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    //intent.setAction(Intent.ACTION_GET_CONTENT);
                    getActivity().startActivityForResult(intent, REQUEST_CODE);
                    break;
                case R.id.updateOkBtn://R.id.button:
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

                                            registerImg();
                                            try {
                                                Thread.sleep(500);
                                                Log.d(TAG, "시간 지연");
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            } // 시간 지연
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
                    imgView.setImageResource(R.drawable.guest);
                    mainactivity.porofileUpdateCancel();
                    break;
            }
        }
    };

    public void registerImg() {
        authService = ServiceGenerator.createService(AuthService.class, JwtToken.getToken());

        if (authService != null) {
            authService.putImgurlData(body)
                    .enqueue(new Callback<ProfileImgDTO>() {
                        @Override
                        public void onResponse(Call<ProfileImgDTO> call, Response<ProfileImgDTO> response) {
                            ProfileImgDTO data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "putProfileImgData : 성공,\nresponseBody : " + data);
                                Log.d(TAG, "=====================================================================");
                            } else {
                                try {
                                    Log.d(TAG, "putProfileImgData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ProfileImgDTO> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.toString());
                        }
                    });
        }
    } // 이미지 업로드

    public void getImgData(Bitmap img, Uri uri) {
        imgView.setImageBitmap(img);

        Log.d(TAG, "uri는?? " + uri);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null );
        cursor.moveToNext();
        @SuppressLint("Range") String absolutePath = cursor.getString( cursor.getColumnIndex( "_data" ) );
        cursor.close();
        Log.d(TAG, "절대경로 : " + absolutePath);
        // 절대경로 얻기

        /*Cursor c = context.getContentResolver().query(Uri.parse(uri.toString()), null,null,null,null);
        c.moveToNext();
        @SuppressLint("Range") String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA)); // 절대경로 얻기
        Log.d(TAG, "절대경로 : " + absolutePath);*/

        File f = new File(absolutePath);
        Log.d(TAG, "file : " + f.toString());

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), f);
        Log.d(TAG, "requestBody : " + requestBody.toString());

        body = MultipartBody.Part.createFormData("file", f.getName(), requestBody);
        Log.d(TAG, "body" + body.toString());
    }

    public void release() {
        email = null;
        name = null;
        gender = null;
        age = 0;
        height = 0;
        weight = 0;
    }
}
