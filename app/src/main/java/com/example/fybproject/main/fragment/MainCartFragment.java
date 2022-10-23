package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.fybproject.dto.shopDTO.MainDTO;
import com.example.fybproject.dto.wishlistDTO.WishAddDTO;
import com.example.fybproject.dto.wishlistDTO.WishlistDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.listView.cart.CartListItem;
import com.example.fybproject.listView.cart.CartListItemAdapter;
import com.example.fybproject.listView.home.RecommendShopListItem;
import com.example.fybproject.listView.home.RecommendShopListItemAdapter;
import com.example.fybproject.mediator.MainUserDataMediator;
import com.example.fybproject.service.ShopService;
import com.example.fybproject.service.WishlistService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCartFragment extends Fragment {
    View view;

    TextView addBtn, addCancelBtn, addSetBtn;
    LinearLayout defaultBtnGroup, addItemBtnGroup, selectItemBtnGroup
            , addItem;
    EditText cartItemName,cartItemNote, cartItemPrice, cartItemUrl;

    private RecyclerView cartRecyclerView;
    private CartListItemAdapter adapter;
    private ArrayList<CartListItem> arr;
    private Context context;

    MainActivity mainactivity;

    long pid;
    int price, px, py;
    String pname, notes, pUrl;

    private WishlistService wishlistService;

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
        view =  inflater.inflate(R.layout.fragment_main_cart, container, false);

        px = 960;

        init();
        loadCartList();

        addBtn.setOnClickListener(listener);
        addSetBtn.setOnClickListener(listener);
        addCancelBtn.setOnClickListener(listener);

        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addBtn:
                    cartItemName.setText(null);
                    cartItemNote.setText(null);
                    cartItemPrice.setText(null);
                    cartItemUrl.setText(null);
                    py = 570;
                    cartRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(px, py));
                    addItem.setVisibility(View.VISIBLE);
                    defaultBtnGroup.setVisibility(View.GONE);
                    addItemBtnGroup.setVisibility(View.VISIBLE);
                    break;
                case R.id.addCancelBtn:
                    cartItemName.setText(null);
                    cartItemNote.setText(null);
                    cartItemPrice.setText(null);
                    cartItemUrl.setText(null);
                    py = 900;
                    cartRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(px, py));
                    addItem.setVisibility(View.GONE);
                    defaultBtnGroup.setVisibility(View.VISIBLE);
                    addItemBtnGroup.setVisibility(View.GONE);
                    break;
                case R.id.addSetBtn:
                    py = 900;
                    cartRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(px, py));
                    addItem.setVisibility(View.GONE);
                    defaultBtnGroup.setVisibility(View.VISIBLE);
                    addItemBtnGroup.setVisibility(View.GONE);

                    inputAddData();
                    WishAddDTO wishAddDTO = new WishAddDTO(pname, notes, price, pUrl);
                    wishlistService = ServiceGenerator.createService(WishlistService.class, JwtToken.getToken());

                    if (wishlistService != null) {
                        wishlistService.postWishData(wishAddDTO)
                                .enqueue(new Callback<WishAddDTO>() {
                                    @Override
                                    public void onResponse(Call<WishAddDTO> call, Response<WishAddDTO> response) {
                                        WishAddDTO data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(TAG, "addWishList : 성공,\nresponseBody : " + data);
                                            Log.d(TAG, "=====================================================================");

                                            loadCartList();
                                            release();

                                            cartItemName.setText(null);
                                            cartItemNote.setText(null);
                                            cartItemPrice.setText(null);
                                            cartItemUrl.setText(null);
                                        } else {
                                            try {
                                                Log.d(TAG, "addWishList : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<WishAddDTO> call, Throwable t) {
                                        Log.d(TAG, "onFailure: " + t.toString());
                                    }
                                });

                        break;
                    }
            }
        }
    };

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

                                        pid = real.getPid();
                                        pname = real.getPname();
                                        notes = real.getNotes();
                                        price = real.getPrice();
                                        pUrl = real.getPurl();
                                        Log.d(TAG, "pid[" + index + "]: " + pid + ", pname[" + index + "]: " + pname + ", notes[" + index
                                                + "]: " + notes + ", price[" + index + "]: " + price + ", pUrl[" + index + "]: " + pUrl);
                                        arr.add(new CartListItem(pid, pname, notes, price, pUrl));
                                        // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                        if (data.size() - 1 == index)
                                            adapter.setList(arr);

                                        index++;
                                    }

                                    release();
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
        }

    public void init() {
            addBtn = view.findViewById(R.id.addBtn);
            defaultBtnGroup = view.findViewById(R.id.defaultBtnGroup);
            addItem = view.findViewById(R.id.addItem);
            addItemBtnGroup = view.findViewById(R.id.addItemBtnGroup);
            selectItemBtnGroup = view.findViewById(R.id.selectItemBtnGroup);
            addSetBtn = view.findViewById(R.id.addSetBtn);
            addCancelBtn = view.findViewById(R.id.addCancelBtn);
            cartItemName = view.findViewById(R.id.addCartItemName);
            cartItemNote = view.findViewById(R.id.addCartItemNote);
            cartItemPrice = view.findViewById(R.id.addCartItemPrice);
            cartItemUrl = view.findViewById(R.id.addCartItemUrl);
            cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        }

    public void inputAddData() {
        try {
            if(cartItemName.getText().toString() == null && cartItemNote.getText().toString() == null &&
                    cartItemPrice.getText().toString() == null && cartItemUrl.getText().toString() == null ) {
                Toast.makeText(context.getApplicationContext(), "모든 항목을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pname = cartItemName.getText().toString();
        notes = cartItemNote.getText().toString();
        price = Integer.parseInt(cartItemPrice.getText().toString());
        pUrl = cartItemUrl.getText().toString();
    }

    public void release() {
        pid = 0;
        pname = null;
        notes = null;
        price = 0;
        pUrl = null;
    }

}

