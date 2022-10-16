package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.LoginDTO;
import com.example.fybproject.dto.shopDTO.SearchDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.listView.search.SearchListItem;
import com.example.fybproject.listView.search.SearchListItemAdapter;
import com.example.fybproject.service.AuthService;
import com.example.fybproject.service.ShopService;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSearchFragment extends Fragment {

    View view;

    EditText inputShop;
    ImageView shopSearchBtn;

    ListView searchListView;

    SearchListItemAdapter adapter;

    private ShopService shopService;

    String shop;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_search, container, false);

        init();

        adapter = new SearchListItemAdapter();

        shopService = ServiceGenerator.createService(ShopService.class);

        adapter.clearItem();

        if (shopService != null) {
            shopService.getSearchData()
                    .enqueue(new Callback<ArrayList<SearchDTO>>() {
                        @Override
                        public void onResponse(Call<ArrayList<SearchDTO>> call, Response<ArrayList<SearchDTO>> response) {
                            ArrayList<SearchDTO> data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "getSearchShop : 성공,\nresponseBody : " + data);


                                String name1 = null, url1 = null, name2 = null, url2 = null;
                                int index = 0;
                                for (SearchDTO real : data) {

                                    if (data.size() % 2 == 1) {
                                        name1 = real.getShop();
                                        url1 = real.getSurl();
                                        if(data.get(index + 1) == null) {
                                            adapter.addItem(new SearchListItem(name1, url1, null, null));
                                        }
                                    }
                                    if (data.size() % 2 == 0) {
                                        name2 = real.getShop();
                                        url2 = real.getSurl();
                                        if(data.get(index + 1) == null) {
                                            adapter.addItem(new SearchListItem(name1, url1, name2, url2));
                                        }
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
                                        Log.d(TAG, "postSearchShop : 성공,\nresponseBody : " + data);
                                        adapter.clearItem();

                                        String name1 = null, url1 = null, name2 = null, url2 = null;
                                        int index = 0;
                                        for (SearchDTO real : data) {

                                            if (data.size() % 2 == 1) {
                                                name1 = real.getShop();
                                                url1 = real.getSurl();
                                                if(data.get(index + 1) == null) {
                                                    adapter.addItem(new SearchListItem(name1, url1, null, null));
                                                }
                                            }
                                            if (data.size() % 2 == 0) {
                                                name2 = real.getShop();
                                                url2 = real.getSurl();
                                                if(data.get(index + 1) == null) {
                                                    adapter.addItem(new SearchListItem(name1, url1, name2, url2));
                                                }
                                            }
                                            index++;
                                        }
                                    } else {
                                        try {
                                            Log.d(TAG, "postSearchShop : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
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
        searchListView = view.findViewById(R.id.searchListView);
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
