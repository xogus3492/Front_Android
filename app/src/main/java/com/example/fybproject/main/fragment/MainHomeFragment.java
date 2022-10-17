package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.shopDTO.MainDTO;
import com.example.fybproject.dto.shopDTO.SearchDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.listView.home.RecommendShopListItem;
import com.example.fybproject.listView.home.RecommendShopListItemAdapter;
import com.example.fybproject.listView.search.SearchListItem;
import com.example.fybproject.listView.search.SearchListItemAdapter;
import com.example.fybproject.service.ShopService;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainHomeFragment extends Fragment {

    View view;

    TextView userName;

    ListView recommendShop;
    RecommendShopListItemAdapter rAdapter;

    String name;
    char gender;
    int age;

    private ShopService shopService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_home, container, false);

        init();

        rAdapter = new RecommendShopListItemAdapter();

        shopService = ServiceGenerator.createService(ShopService.class, JwtToken.getToken());

        rAdapter.clearItem();

        if (shopService != null) {
            shopService.getMainData()
                    .enqueue(new Callback<ArrayList<MainDTO>>() {
                        @Override
                        public void onResponse(Call<ArrayList<MainDTO>> call, Response<ArrayList<MainDTO>> response) {
                            ArrayList<MainDTO> data = response.body();
                            Headers header = response.headers();
                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "getMainData : 성공,\nresponseBody : " + data);
                                Log.d(TAG, "=====================================================================");

                                int index = 0;
                                for (MainDTO real : data) {
                                    if(data.size() - 1 == index) {
                                        name = real.getName();
                                        age = real.getAge();
                                        gender = real.getGender();
                                        userName.setText(name);
                                        return;
                                    }

                                    String name = real.getShop();
                                    String url = real.getSurl();
                                    rAdapter.addItem(new RecommendShopListItem(name, url));

                                    index++;
                                }
                            } else {
                                try {
                                    Log.d(TAG, "getMainData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<MainDTO>> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.toString());
                        }
                    });
        } // 쇼핑몰 조회

        return view;
    }

    public void init() {
        userName = view.findViewById(R.id.userName);
        recommendShop = view.findViewById(R.id.recommendShop);
    }
}
