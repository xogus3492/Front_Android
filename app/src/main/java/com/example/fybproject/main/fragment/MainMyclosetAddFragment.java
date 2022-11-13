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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.myClosetDTO.ClosetAddDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetImgDTO;
import com.example.fybproject.interceptor.JwtToken;
import com.example.fybproject.listView.closet.ClosetListItem;
import com.example.fybproject.listView.closet.ClosetListItemAdapter;
import com.example.fybproject.service.MyClosetService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMyclosetAddFragment extends Fragment {
    View view;

    TextView setItem, cancelItem;
    EditText closetItemName, closetItemNote, closetItemKind;
    ImageView closetItemImg;

    private static final int REQUEST_CODE = 1000;
    private MultipartBody.Part body;

    private MainActivity mainactivity;
    private Context context;

    private MyClosetService myClosetService;

    String pname, pnote, pkind;

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
        view = inflater.inflate(R.layout.fragment_mypage_mycloset_add, container, false);

        init();

        setItem.setOnClickListener(listener);
        cancelItem.setOnClickListener(listener);
        closetItemImg.setOnClickListener(listener);

        return view;
    }

    public void init() {
        closetItemImg = view.findViewById(R.id.addClosetItemImg);
        closetItemName = view.findViewById(R.id.addClosetItemName);
        closetItemNote = view.findViewById(R.id.addClosetItemNote);
        closetItemKind = view.findViewById(R.id.addClosetItemKind);
        setItem = view.findViewById(R.id.mAddSetBtn);
        cancelItem = view.findViewById(R.id.mAddCancelBtn);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mAddSetBtn:
                    inputAddData();
                    ClosetAddDTO closetAddDTO = new ClosetAddDTO(pname, pnote, pkind);
                    myClosetService = ServiceGenerator.createService(MyClosetService.class, JwtToken.getToken());

                    if (myClosetService != null) {
                        myClosetService.postClosetData(closetAddDTO)
                                .enqueue(new Callback<ArrayList<ClosetAddDTO>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<ClosetAddDTO>> call, Response<ArrayList<ClosetAddDTO>> response) {
                                        ArrayList<ClosetAddDTO> data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(TAG, "addClosetData : 성공,\nresponseBody : " + data);
                                            Log.d(TAG, "=====================================================================");
                                            long id = 0;

                                            for(ClosetAddDTO real : data) {
                                                //Log.d(TAG, "real: " + real.toString());
                                                id = real.getId();
                                                regiClosetImg(id); // 이미지 업로드
                                            }
                                            try {
                                                Thread.sleep(1500);
                                                Log.d(TAG, "시간 지연");
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            } // 시간 지연

                                            release();
                                            mainactivity.changeToMyClosetFragment();

                                        } else {
                                            try {
                                                Log.d(TAG, "addClosetData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<ClosetAddDTO>> call, Throwable t) {
                                        Log.d(TAG, "onFailure: " + t.toString());
                                    }
                                });
                    }
                    break; // 내 옷장 등록

                case R.id.mAddCancelBtn:
                    release();
                    mainactivity.changeToMyClosetFragment();
                    break; // 취소 버튼
                case R.id.addClosetItemImg:
                    //ProfileImageActivity.verifyStoragePermissions(getActivity());

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    //intent.setType("image/*");
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    //intent.setAction(Intent.ACTION_GET_CONTENT);
                    getActivity().startActivityForResult(intent, REQUEST_CODE);
                    break; // 이미지 설정
            }
        }
    };

    public void regiClosetImg(long id) {
        myClosetService = ServiceGenerator.createService(MyClosetService.class, JwtToken.getToken());

        if (myClosetService != null) {
            myClosetService.putClosetData(id,body)
                    .enqueue(new Callback<ClosetImgDTO>() {
                        @Override
                        public void onResponse(Call<ClosetImgDTO> call, Response<ClosetImgDTO> response) {
                            ClosetImgDTO data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "putClosetData : 성공,\nresponseBody : " + data);
                                Log.d(TAG, "=====================================================================");
                            } else {
                                try {
                                    Log.d(TAG, "putClosetData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ClosetImgDTO> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.toString());
                        }
                    });
        }
    } // 이미지 업로드

    public void getImgData(Bitmap img, Uri uri) {
        closetItemImg.setImageBitmap(img);

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
        //Log.d(TAG, "절대경로 : " + absolutePath);*/

        File f = new File(absolutePath);
        Log.d(TAG, "file : " + f.toString());

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), f);
        Log.d(TAG, "requestBody : " + requestBody.toString());

        body = MultipartBody.Part.createFormData("file", f.getName(), requestBody);
        Log.d(TAG, "body" + body.toString());
    }

    public void inputAddData() {
        try {
            if(closetItemName.getText().toString() == null && closetItemNote.getText().toString() == null &&
                    closetItemKind.getText().toString() == null) {
                Toast.makeText(context.getApplicationContext(), "모든 항목을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pname = closetItemName.getText().toString();
        pnote = closetItemNote.getText().toString();
        pkind = closetItemKind.getText().toString();
    }

    public void release() {
        pname = null;
        pnote = null;
        pkind = null;
        body = null;

        closetItemName.setText("");
        closetItemNote.setText("");
        closetItemKind.setText("");
        closetItemImg.setImageResource(R.drawable.closet_img);
    }
}
