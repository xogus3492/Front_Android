package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.fybproject.dto.wishlistDTO.WishAddDTO;
import com.example.fybproject.dto.wishlistDTO.WishlistDTO;
import com.example.fybproject.interceptor.JwtToken;
import com.example.fybproject.listView.cart.CartListItem;
import com.example.fybproject.listView.cart.CartListItemAdapter;
import com.example.fybproject.service.WishlistService;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCartFragment extends Fragment {
    View view;

    TextView addBtn, noCart
            , totalPrice;

    private RecyclerView cartRecyclerView;
    private CartListItemAdapter adapter;
    private ArrayList<CartListItem> arr;
    private Context context;

    MainActivity mainactivity;

    long pid;
    int price;
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

        mainactivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);

        init();
        loadCartList();

        addBtn.setOnClickListener(listener);

        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mainactivity.changeToMyCartAddFragment();
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
                                if(data != null) {
                                    noCart.setVisibility(View.GONE);
                                    cartRecyclerView.setVisibility(View.VISIBLE);
                                }

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

                                //totalPrice.setText(String.valueOf(CartMediator.getPrice()));
                            } else {
                                try {
                                    Log.d(TAG, "getWisgData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                    if(data == null) {
                                        Log.d(TAG, "2 수행됨");
                                        noCart.setVisibility(View.VISIBLE);
                                        cartRecyclerView.setVisibility(View.GONE);
                                    }
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
        }
    }// 장바구니 조회

    public void init() {
        addBtn = view.findViewById(R.id.addBtn);
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        noCart = view.findViewById(R.id.noCart);
        //totalPrice = view.findViewById(R.id.totalPrice);
    }

}

