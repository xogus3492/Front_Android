package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.listView.closet.ClosetListItem;
import com.example.fybproject.listView.closet.ClosetListItemAdapter;
import com.example.fybproject.service.MyClosetService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMyclosetFragment extends Fragment {
    View view;

    TextView addBtn, addCancelBtn, addSetBtn;
    LinearLayout defaultBtnGroup, addItemBtnGroup
            , addItem;
    EditText closetItemName, closetItemNote, closetItemKind;

    private RecyclerView closetRecyclerView;
    private ClosetListItemAdapter adapter;
    private ArrayList<ClosetListItem> arr;
    private Context context;

    MainActivity mainactivity;

    long id;
    int px, py;
    String pname, pnote, pkind;

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

                                            for(ClosetAddDTO real : data) {
                                                Log.d(TAG, "real: " + real.toString());
                                            }

                                            loadClosetList();
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
                                    Log.d(TAG, "id[" + index + "]: " + id + ", pname[" + index + "]: " + pname + ", pnotes[" + index
                                            + "]: " + pnote + ", pkind[" + index + "]: " + pkind);
                                    arr.add(new ClosetListItem(id, pname, pnote, pkind));
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
        } // 내 옷장 조회
    }

    public void init() {
        addBtn = view.findViewById(R.id.mAddBtn);
        defaultBtnGroup = view.findViewById(R.id.mDefaultBtnGroup);
        addItemBtnGroup = view.findViewById(R.id.mAddItemBtnGroup);
        addItem = view.findViewById(R.id.addClosetItem);
        addSetBtn = view.findViewById(R.id.mAddSetBtn);
        addCancelBtn = view.findViewById(R.id.mAddCancelBtn);
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