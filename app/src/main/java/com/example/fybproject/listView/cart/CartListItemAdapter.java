package com.example.fybproject.listView.cart;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.service.controls.ControlsProviderService;
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

import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.wishlistDTO.WishDeleteDTO;
import com.example.fybproject.dto.wishlistDTO.WishUpdateDTO;
import com.example.fybproject.dto.wishlistDTO.WishlistDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.mediator.CartMediator;
import com.example.fybproject.service.WishlistService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListItemAdapter extends RecyclerView.Adapter<CartListItemAdapter.ItemViewHolder> {
    View view;

    private ArrayList<CartListItem> listData;

    private JwtToken JwtToken;
    private RecyclerView cartRecyclerView;
    private CartListItemAdapter adapter;
    private ArrayList<CartListItem> arr;
    private Context context;

    private WishlistService wishlistService;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlist_item, parent, false);

        cartRecyclerView = (RecyclerView) parent;
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

    public void setList(ArrayList<CartListItem> list) {
        this.listData = list;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        EditText name, note, price, url;
        ImageView img, selectItemCancel, updateItem, deleteItem, goUrl;
        LinearLayout layout, selectAction;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cartItemName);
            note = itemView.findViewById(R.id.cartItemNote);
            price = itemView.findViewById(R.id.cartItemPrice);
            url = itemView.findViewById(R.id.cartItemUrl);
            img = itemView.findViewById(R.id.cartItemImage);
            layout = itemView.findViewById(R.id.cartItem);

            selectAction = view.findViewById(R.id.selectAction);
            updateItem = view.findViewById(R.id.changeCartItem);
            deleteItem = view.findViewById(R.id.deleteCartItem);
            goUrl = view.findViewById(R.id.goCartItemUrl);
            selectItemCancel = view.findViewById(R.id.selectItemCancel);
        }

        void onBind(CartListItem data) {
            CartMediator.setPrice(data.getPrice());

            name.setText(data.getPname());
            note.setText(data.getNotes());
            price.setText(String.valueOf(data.getPrice()));
            url.setText(data.getpUrl());

            name.setFocusableInTouchMode(false);
            note.setFocusableInTouchMode(false);
            price.setFocusableInTouchMode(false);

            layout.setOnClickListener(listener);
            name.setOnClickListener(listener);
            note.setOnClickListener(listener);
            price.setOnClickListener(listener);

            selectItemCancel.setOnClickListener(selectedListener);

            updateItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "수정 내용 : " + data.getPid() + name.getText().toString() + note.getText().toString() + url.getText().toString() + Integer.parseInt(price.getText().toString()));
                    WishUpdateDTO wishUpdateDTO = new WishUpdateDTO(data.getPid(), name.getText().toString(), note.getText().toString(), url.getText().toString(), Integer.parseInt(price.getText().toString()));
                    wishlistService = ServiceGenerator.createService(WishlistService.class, JwtToken.getToken());

                    if (wishlistService != null) {
                        wishlistService.patchWishData(wishUpdateDTO)
                                .enqueue(new Callback<WishUpdateDTO>() {
                                    @Override
                                    public void onResponse(Call<WishUpdateDTO> call, Response<WishUpdateDTO> response) {
                                        WishUpdateDTO data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(ContentValues.TAG, "cartItemUpdate : 성공,\nresponseBody : " + data);
                                            Log.d(ContentValues.TAG, "=====================================================================");

                                            loadCartList();
                                        } else {
                                            try {
                                                Log.d(ContentValues.TAG, "cartItemUpdate : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<WishUpdateDTO> call, Throwable t) {
                                        Log.d(ContentValues.TAG, "onFailure: " + t.toString());
                                    }
                                });
                    }
                }
            }); // 장바구니 수정

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WishDeleteDTO wishDeleteDTO = new WishDeleteDTO(data.getPid());
                    wishlistService = ServiceGenerator.createService(WishlistService.class, JwtToken.getToken());

                    if (wishlistService != null) {
                        wishlistService.deleteWishData(wishDeleteDTO)
                                .enqueue(new Callback<WishDeleteDTO>() {
                                    @Override
                                    public void onResponse(Call<WishDeleteDTO> call, Response<WishDeleteDTO> response) {
                                        WishDeleteDTO data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(ContentValues.TAG, "cartItemDelete : 성공,\nresponseBody : " + data);
                                            Log.d(ContentValues.TAG, "=====================================================================");

                                            loadCartList();
                                        } else {
                                            try {
                                                Log.d(ContentValues.TAG, "cartItemDelete : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<WishDeleteDTO> call, Throwable t) {
                                        Log.d(ContentValues.TAG, "onFailure: " + t.toString());
                                    }
                                });
                    }
                }
            }); // 장바구니 삭제

            goUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = data.getpUrl();
                    Context context = view.getContext();
                    Log.d(ControlsProviderService.TAG, "쇼핑몰 주소 : " + url);
                    Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intentUrl);
                }
            }); // 쇼핑몰 이동
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(ContentValues.TAG, "아이템 클릭됨");

                name.setFocusableInTouchMode(true);
                note.setFocusableInTouchMode(true);
                price.setFocusableInTouchMode(true);
                name.setCursorVisible(true);
                note.setCursorVisible(true);
                price.setCursorVisible(true);

                url.setVisibility(View.VISIBLE);
                selectItemCancel.setVisibility(View.VISIBLE);
                selectAction.setVisibility(View.VISIBLE);
            }
        }; // 아이템 선택

        View.OnClickListener selectedListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackground(view.getResources().getDrawable(R.drawable.list_bg));

                name.setFocusableInTouchMode(false);
                note.setFocusableInTouchMode(false);
                price.setFocusableInTouchMode(false);
                name.setCursorVisible(false);
                note.setCursorVisible(false);
                price.setCursorVisible(false);

                url.setVisibility(View.GONE);
                selectItemCancel.setVisibility(View.GONE);
                selectAction.setVisibility(View.GONE);
            }
        };// 아이템 선택 취소

        public void loadCartList() {
            adapter = new CartListItemAdapter();

            cartRecyclerView.setAdapter(adapter);
            cartRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

            wishlistService = ServiceGenerator.createService(WishlistService.class, JwtToken.getToken());

            if (wishlistService != null) {
                wishlistService.getWishData()
                        .enqueue(new Callback<ArrayList<WishlistDTO>>() {
                            @Override
                            public void onResponse(Call<ArrayList<WishlistDTO>> call, Response<ArrayList<WishlistDTO>> response) {
                                ArrayList<WishlistDTO> data = response.body();
                                if (response.isSuccessful() == true) {
                                    Log.d(TAG, "getWishData : 성공,\nresponseBody : " + data);
                                    Log.d(TAG, "=====================================================================");

                                    int index = 0;
                                    arr = new ArrayList<>();
                                    for (WishlistDTO real : data) {
                                        Log.d(TAG, "real: " + real.toString());

                                        long pid = real.getPid();
                                        String pname = real.getPname();
                                        String notes = real.getNotes();
                                        int price = real.getPrice();
                                        String pUrl = real.getPurl();
                                        Log.d(TAG, "pid[" + index + "]: " + pid + ", pname[" + index + "]: " + pname + ", notes[" + index
                                                + "]: " + notes + ", price[" + index + "]: " + price + ", pUrl[" + index + "]: " + pUrl);
                                        arr.add(new CartListItem(pid, pname, notes, price, pUrl));
                                        // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                        if (data.size() - 1 == index)
                                            adapter.setList(arr);

                                        index++;
                                    }
                                } else {
                                    try {
                                        Log.d(TAG, "getWisgData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<WishlistDTO>> call, Throwable t) {
                                Log.d(TAG, "onFailure: " + t.toString());
                            }
                        });
            } // 장바구니 조회
        } // 장바구니 조회
    }
}
