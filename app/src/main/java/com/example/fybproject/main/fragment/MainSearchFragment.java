package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.shopDTO.SearchDTO;
import com.example.fybproject.listView.search.SearchListItem;
import com.example.fybproject.listView.search.SearchListItemAdapter;
import com.example.fybproject.service.ShopService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSearchFragment extends Fragment {
    View view;

    EditText inputShop;
    ImageView shopSearchBtn;

    private RecyclerView recyclerView;
    private SearchListItemAdapter adapter;
    private ArrayList<SearchListItem> arr;
    private Context mContext;

    String shop;

    private ShopService shopService;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_search, container, false);

        init();

        adapter = new SearchListItemAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

        shopService = ServiceGenerator.createService(ShopService.class);

        if (shopService != null) {
            shopService.getSearchData()
                    .enqueue(new Callback<ArrayList<SearchDTO>>() {
                        @Override
                        public void onResponse(Call<ArrayList<SearchDTO>> call, Response<ArrayList<SearchDTO>> response) {
                            ArrayList<SearchDTO> data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "getSearchShop : 성공,\nresponseBody : " + data);
                                Log.d(TAG, "=====================================================================");

                                int index = 0;
                                String shop, surl, simg;
                                arr = new ArrayList<>();
                                for (SearchDTO real : data) {
                                    Log.d(TAG, "real: " + real.toString());

                                    shop = real.getShop();
                                    surl = real.getSurl();
                                    simg = real.getSimg();
                                    Log.d(TAG, "shop" + index + ": " + shop + "\nsurl" + index + ": " + surl);
                                    arr.add(new SearchListItem(shop, surl, simg));
                                    // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                    if(data.size() - 1 == index) {
                                        adapter.setList(arr);
                                        break;
                                    }

                                    index++;
                                }
                            } else {
                                try {
                                    Log.d(TAG, "getSearchShop : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<SearchDTO>> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.toString());
                        }
                    });
        } // 쇼핑몰 조회

        shopSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                SearchDTO searchDTO = new SearchDTO(shop);
                shopService = ServiceGenerator.createService(ShopService.class);

                if (shopService != null) {
                    shopService.postSearchData(searchDTO)
                            .enqueue(new Callback<ArrayList<SearchDTO>>() {
                                @Override
                                public void onResponse(Call<ArrayList<SearchDTO>> call, Response<ArrayList<SearchDTO>> response) {
                                    ArrayList<SearchDTO> data = response.body();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "getSearchShop : 성공,\nresponseBody : " + data);
                                        Log.d(TAG, "=====================================================================");

                                        int index = 0;
                                        String shop, surl, simg;
                                        arr = new ArrayList<>();
                                        for (SearchDTO real : data) {
                                            Log.d(TAG, "real: " + real.toString());

                                            shop = real.getShop();
                                            surl = real.getSurl();
                                            simg = real.getSimg();
                                            Log.d(TAG, "shop" + index + ": " + shop + "\nsurl" + index + ": " + surl);
                                            arr.add(new SearchListItem(shop, surl, simg));
                                            // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                            if(data.size() - 1 == index) {
                                                adapter.setList(arr);
                                                break;
                                            }

                                            index++;
                                        }
                                    } else {
                                        try {
                                            Log.d(TAG, "getSearchShop : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<SearchDTO>> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 쇼핑몰 검색

        return view;
    }

    public void init() {
        recyclerView = view.findViewById(R.id.searchRecyclerView);
        inputShop = view.findViewById(R.id.inputShop);
        shopSearchBtn = view.findViewById(R.id.shopSearchBtn);
    }

    public void inputData() {
        if (inputShop.getText().toString() == null) {
            Toast.makeText(view.getContext().getApplicationContext(), "검색할 쇼핑몰을 입력 해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        shop = inputShop.getText().toString();
    }
}
