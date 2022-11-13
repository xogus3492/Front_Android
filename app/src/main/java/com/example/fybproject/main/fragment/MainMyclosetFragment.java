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

    TextView addBtn;

    private RecyclerView closetRecyclerView;
    private ClosetListItemAdapter adapter;
    private ArrayList<ClosetListItem> arr;

    private Context context;
    private MainActivity mainactivity;

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

        init();
        loadClosetList();

        addBtn.setOnClickListener(listener);

        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                    mainactivity.changeToClosetAddFragment();
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

                                    long id = real.getId();
                                    String pname = real.getPname();
                                    String pnote = real.getPnotes();
                                    String pkind = real.getPkind();
                                    String closetImagePath = real.getClosetImagePath();
                                    Log.d(TAG, "id[" + index + "]: " + id + ", pname[" + index + "]: " + pname + ", pnotes[" + index
                                            + "]: " + pnote + ", pkind[" + index + "]: " + pkind + ", url[" + index + "]: " + closetImagePath);
                                    arr.add(new ClosetListItem(id, pname, pnote, pkind, closetImagePath));
                                    // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                    if (data.size() - 1 == index)
                                        adapter.setList(arr);

                                    index++;
                                }
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

    public void init() {
        addBtn = view.findViewById(R.id.mAddBtn);
        closetRecyclerView = view.findViewById(R.id.cRecyclerView);
    }



}