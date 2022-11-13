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
import com.example.fybproject.dto.myClosetDTO.ClosetImgDTO;
import com.example.fybproject.dto.wishlistDTO.WishAddDTO;
import com.example.fybproject.dto.wishlistDTO.WishlistDTO;
import com.example.fybproject.interceptor.JwtToken;
import com.example.fybproject.listView.cart.CartListItem;
import com.example.fybproject.listView.cart.CartListItemAdapter;
import com.example.fybproject.service.MyClosetService;
import com.example.fybproject.service.WishlistService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCartAddFragment extends Fragment {
    View view;

    TextView setItem, cancelItem;
    EditText cartItemName, cartItemNote, cartItemPrice, cartItemUrl;
    ImageView cartItemImg;

    private MainActivity mainactivity;
    private Context context;

    private WishlistService wishlistService;

    int price;
    String pname, notes, pUrl;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainactivity = (MainActivity) getActivity();
        this.context = context;
    } // 메인액티비티 객체 가져오기

    @Override
    public void onDetach() {
        super.onDetach();
        mainactivity = null;
    } // 메인액티비티 객체 해제

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_cart_add, container, false);

        init();

        setItem.setOnClickListener(listener);
        cancelItem.setOnClickListener(listener);

        return view;
    }

    public void init() {
        cartItemImg = view.findViewById(R.id.addClosetItemImg);
        cartItemName = view.findViewById(R.id.addCartItemName);
        cartItemNote = view.findViewById(R.id.addCartItemNote);
        cartItemPrice = view.findViewById(R.id.addCartItemPrice);
        cartItemUrl = view.findViewById(R.id.addCartItemUrl);
        setItem = view.findViewById(R.id.addSetBtn);
        cancelItem = view.findViewById(R.id.addCancelBtn);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addSetBtn:
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

                                            release();
                                            mainactivity.changeToMyCartFragment();
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
                    }
                    break; // 장바구니 등록

                case R.id.addCancelBtn:
                    release();
                    mainactivity.changeToMyCartFragment();
                    break; // 취소 버튼
            }
        }

    };

    public void inputAddData() {
        try {
            if (cartItemName.getText().toString() == null && cartItemNote.getText().toString() == null &&
                    cartItemPrice.getText().toString() == null && cartItemUrl.getText().toString() == null) {
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
            pname = null;
            notes = null;
            price = 0;
            pUrl = null;

            cartItemName.setText("");
            cartItemNote.setText("");
            cartItemPrice.setText("");
            cartItemUrl.setText("");
        }


}
