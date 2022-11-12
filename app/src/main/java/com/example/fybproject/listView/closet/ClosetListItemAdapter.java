package com.example.fybproject.listView.closet;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.myClosetDTO.ClosetDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetDeleteDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetUpdateDTO;
import com.example.fybproject.interceptor.JwtToken;
import com.example.fybproject.service.MyClosetService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClosetListItemAdapter extends RecyclerView.Adapter<ClosetListItemAdapter.ItemViewHolder> {
    View view;

    private ArrayList<ClosetListItem> listData;
    private RecyclerView closetRecyclerView;
    private ClosetListItemAdapter adapter;
    private ArrayList<ClosetListItem> arr;
    private Context context;

    private MyClosetService myClosetService;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.closetlist_item, parent, false);

        closetRecyclerView = (RecyclerView) parent;
        context = parent.getContext();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        if (listData != null) {
            return listData.size();
        }
        return 0;
    }

    public void setList(ArrayList<ClosetListItem> list) {
        this.listData = list;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        EditText name, note, kind;
        ImageView img, selectItemCancel, updateItem, deleteItem;
        LinearLayout layout, selectAction;

        String str1, str2, str3;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.closetItemName);
            note = itemView.findViewById(R.id.closetItemNote);
            kind = itemView.findViewById(R.id.closetItemKind);
            img = itemView.findViewById(R.id.closetItemImage);
            layout = itemView.findViewById(R.id.closetItem);

            selectAction = view.findViewById(R.id.mSelectAction);
            updateItem = view.findViewById(R.id.changeClosetItem);
            deleteItem = view.findViewById(R.id.deleteClosetItem);
            selectItemCancel = view.findViewById(R.id.mSelectItemCancel);
        }

        void onBind(ClosetListItem data) {
            name.setText(data.getPname());
            note.setText(data.getPnote());
            kind.setText(data.getPkind());

            // 이미지 불러오기
            if(data.getUrl() != null) {
                String imageUrl = data.getUrl();
                Glide.with(context).load(imageUrl).into(img);
            }

            name.setFocusableInTouchMode(false);
            note.setFocusableInTouchMode(false);
            kind.setFocusableInTouchMode(false);

            layout.setOnClickListener(listener);
            name.setOnClickListener(listener);
            note.setOnClickListener(listener);
            kind.setOnClickListener(listener);

            selectItemCancel.setOnClickListener(selectedListener);

            updateItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Log.d(TAG, "수정 내용 : " + data.getUid() + name.getText().toString() + note.getText().toString() + kind.getText().toString());
                    ClosetUpdateDTO closetUpdateDTO = new ClosetUpdateDTO(data.getUid(), name.getText().toString(), note.getText().toString(), kind.getText().toString());
                    myClosetService = ServiceGenerator.createService(MyClosetService.class, JwtToken.getToken());

                    if (myClosetService != null) {
                        myClosetService.patchClosetData(closetUpdateDTO)
                                .enqueue(new Callback<ClosetUpdateDTO>() {
                                    @Override
                                    public void onResponse(Call<ClosetUpdateDTO> call, Response<ClosetUpdateDTO> response) {
                                        ClosetUpdateDTO data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(ContentValues.TAG, "closetItemUpdate : 성공,\nresponseBody : " + data);
                                            Log.d(ContentValues.TAG, "=====================================================================");

                                            loadClosetList();
                                        } else {
                                            try {
                                                Log.d(ContentValues.TAG, "closetItemUpdate : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ClosetUpdateDTO> call, Throwable t) {
                                        Log.d(ContentValues.TAG, "onFailure: " + t.toString());
                                    }
                                });
                    }
                }
            }); // 내 옷장 수정

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClosetDeleteDTO closetDeleteDTO = new ClosetDeleteDTO(data.getUid());
                    myClosetService = ServiceGenerator.createService(MyClosetService.class, JwtToken.getToken());

                    if (myClosetService != null) {
                        myClosetService.deleteClosetData(closetDeleteDTO)
                                .enqueue(new Callback<ClosetDeleteDTO>() {
                                    @Override
                                    public void onResponse(Call<ClosetDeleteDTO> call, Response<ClosetDeleteDTO> response) {
                                        ClosetDeleteDTO data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(ContentValues.TAG, "closetItemDelete : 성공,\nresponseBody : " + data);
                                            Log.d(ContentValues.TAG, "=====================================================================");

                                            loadClosetList();
                                        } else {
                                            try {
                                                Log.d(ContentValues.TAG, "closetItemDelete : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ClosetDeleteDTO> call, Throwable t) {
                                        Log.d(ContentValues.TAG, "onFailure: " + t.toString());
                                    }
                                });
                    }
                }
            }); // 내 옷장 삭제
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(ContentValues.TAG, "아이템 클릭됨");

                str1 = name.getText().toString();
                str2 = note.getText().toString();
                str3 = kind.getText().toString();

                name.setFocusableInTouchMode(true);
                note.setFocusableInTouchMode(true);
                kind.setFocusableInTouchMode(true);

                name.requestFocus();

                selectItemCancel.setVisibility(View.VISIBLE);
                selectAction.setVisibility(View.VISIBLE);
            }
        }; // 아이템 선택

        View.OnClickListener selectedListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackground(view.getResources().getDrawable(R.drawable.list_bg));

                name.setText(str1);
                note.setText(str2);
                kind.setText(str3);

                name.setFocusableInTouchMode(false);
                note.setFocusableInTouchMode(false);
                kind.setFocusableInTouchMode(false);

                selectItemCancel.setVisibility(View.GONE);
                selectAction.setVisibility(View.GONE);
            }
        };// 아이템 선택 취소

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

                                        long uid = real.getId();
                                        String pname = real.getPname();
                                        String notes = real.getPnotes();
                                        String pkind = real.getPkind();
                                        String url = real.getClosetImagePath();
                                        Log.d(TAG, "uid[" + index + "]: " + uid + ", pname[" + index + "]: " + pname + ", potes[" + index
                                                + "]: " + notes + ", pkind[" + index + "]: " + pkind + ", url[" + index + "]: " + url);
                                        arr.add(new ClosetListItem(uid, pname, notes, pkind, url));
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
        } // 내 옷장 조회
    }
}
