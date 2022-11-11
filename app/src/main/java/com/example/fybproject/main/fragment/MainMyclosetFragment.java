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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fybproject.MainActivity;
import com.example.fybproject.ProfileImageActivity;
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

public class MainMyclosetFragment extends Fragment {
    View view;

    TextView addBtn, addCancelBtn, addSetBtn;
    LinearLayout defaultBtnGroup, addItemBtnGroup
            , addItem;
    EditText closetItemName, closetItemNote, closetItemKind;
    ImageView closetItemImg;

    private RecyclerView closetRecyclerView;
    private ClosetListItemAdapter adapter;
    private ArrayList<ClosetListItem> arr;
    private Context context;

    private static final int REQUEST_CODE = 1000;
    private MultipartBody.Part body;

    MainActivity mainactivity;

    long id;
    int px, py;
    String pname, pnote, pkind, closetImagePath;

    private MyClosetService myClosetService;

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
        view =  inflater.inflate(R.layout.fragment_mypage_mycloset, container, false);

        px = 960;

        init();
        loadClosetList();

        addBtn.setOnClickListener(listener);
        addSetBtn.setOnClickListener(listener);
        addCancelBtn.setOnClickListener(listener);
        closetItemImg.setOnClickListener(listener);

        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.mAddBtn:
                    closetItemName.setText(null);
                    closetItemNote.setText(null);
                    closetItemKind.setText(null);
                    py = 570;
                    closetRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(px, py));
                    addItem.setVisibility(View.VISIBLE);
                    defaultBtnGroup.setVisibility(View.GONE);
                    addItemBtnGroup.setVisibility(View.VISIBLE);
                    break;
                case R.id.mAddCancelBtn:
                    py = 900;
                    closetRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(px, py));
                    addItem.setVisibility(View.GONE);
                    defaultBtnGroup.setVisibility(View.VISIBLE);
                    addItemBtnGroup.setVisibility(View.GONE);
                    break;
                case R.id.mAddSetBtn:
                    py = 900;
                    closetRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(px, py));
                    addItem.setVisibility(View.GONE);
                    defaultBtnGroup.setVisibility(View.VISIBLE);
                    addItemBtnGroup.setVisibility(View.GONE);

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
                                                Log.d(TAG, "real: " + real.toString());
                                                id = real.getId();
                                                regiClosetImg(id); // 이미지 업로드
                                            }

                                            loadClosetList(); // 옷장 조회
                                            release();
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
                case R.id.addClosetItemImg:
                    ProfileImageActivity.verifyStoragePermissions(getActivity());

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    getActivity().startActivityForResult(intent, REQUEST_CODE);
                    break;
            }
        }
    };

    public void loadClosetList() {
        adapter = new ClosetListItemAdapter();

        closetRecyclerView.setAdapter(adapter);
        closetRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        myClosetService = ServiceGenerator.createService(MyClosetService.class, JwtToken.getToken());

        if (myClosetService != null) {
            myClosetService.getClosetData()
                    .enqueue(new Callback<ArrayList<ClosetDTO>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ClosetDTO>> call, Response<ArrayList<ClosetDTO>> response) {
                            ArrayList<ClosetDTO> data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "getClosetData : 성공,\nresponseBody : " + data);
                                Log.d(TAG, "=====================================================================");

                                int index = 0;
                                arr = new ArrayList<>();
                                for (ClosetDTO real : data) {
                                    Log.d(TAG, "real: " + real.toString());

                                    id = real.getId();
                                    pname = real.getPname();
                                    pnote = real.getPnotes();
                                    pkind = real.getPkind();
                                    closetImagePath = real.getClosetImagePath();
                                    Log.d(TAG, "id[" + index + "]: " + id + ", pname[" + index + "]: " + pname + ", pnotes[" + index
                                            + "]: " + pnote + ", pkind[" + index + "]: " + pkind + ", url[" + index + "]: " + closetImagePath);
                                    arr.add(new ClosetListItem(id, pname, pnote, pkind, closetImagePath));
                                    // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                    if (data.size() - 1 == index)
                                        adapter.setList(arr);

                                    index++;
                                }
                                release();
                            } else {
                                try {
                                    Log.d(TAG, "getClosetData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<ClosetDTO>> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.toString());
                        }
                    });
        }
    }// 내 옷장 조회

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

    public void getImgData(Bitmap img,Uri uri) {
        closetItemImg.setImageBitmap(img);

        Cursor c = context.getContentResolver().query(Uri.parse(uri.toString()), null,null,null,null);
        c.moveToNext();
        @SuppressLint("Range") String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA)); // 절대경로 얻기
        //Log.d(TAG, "절대경로 : " + absolutePath);

        File f = new File(absolutePath);
        //Log.d(TAG, "file : " + f.toString());

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), f);
        //Log.d(TAG, "requestBody : " + requestBody.toString());

        body = MultipartBody.Part.createFormData("file", f.getName(), requestBody);
        //Log.d(TAG, "body" + body.toString());
    }

    public void init() {
        addBtn = view.findViewById(R.id.mAddBtn);
        defaultBtnGroup = view.findViewById(R.id.mDefaultBtnGroup);
        addItemBtnGroup = view.findViewById(R.id.mAddItemBtnGroup);
        addItem = view.findViewById(R.id.addClosetItem);
        addSetBtn = view.findViewById(R.id.mAddSetBtn);
        addCancelBtn = view.findViewById(R.id.mAddCancelBtn);
        closetItemImg = view.findViewById(R.id.addClosetItemImg);
        closetItemName = view.findViewById(R.id.addClosetItemName);
        closetItemNote = view.findViewById(R.id.addClosetItemNote);
        closetItemKind = view.findViewById(R.id.addClosetItemKind);
        closetRecyclerView = view.findViewById(R.id.cRecyclerView);
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
        id = 0;
        pname = null;
        pnote = null;
        pkind = null;
    }

}