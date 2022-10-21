package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.shopDTO.MainDTO;
import com.example.fybproject.dto.shopDTO.SearchDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.listView.home.RecommendShopListItem;
import com.example.fybproject.listView.home.RecommendShopListItemAdapter;
import com.example.fybproject.listView.search.SearchListItem;
import com.example.fybproject.listView.search.SearchListItemAdapter;
import com.example.fybproject.mediator.MainUserDataMediator;
import com.example.fybproject.service.ShopService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainHomeFragment extends Fragment {
    View view;

    TextView userName;

    private RecyclerView recommendRecyclerView;
    private RecommendShopListItemAdapter rAdapter;
    private ArrayList<RecommendShopListItem> arr;
    private Context mContext;

    String shop, surl;
    String name;

    private ShopService shopService;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_home, container, false);

        init();

        rAdapter = new RecommendShopListItemAdapter();

        recommendRecyclerView.setAdapter(rAdapter);
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));

        shopService = ServiceGenerator.createService(ShopService.class, JwtToken.getToken());

        if (shopService != null) {
            shopService.getMainData()
                    .enqueue(new Callback<ArrayList<MainDTO>>() {
                        @Override
                        public void onResponse(Call<ArrayList<MainDTO>> call, Response<ArrayList<MainDTO>> response) {
                            ArrayList<MainDTO> data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "getMainData : 성공,\nresponseBody : " + data);
                                Log.d(TAG, "=====================================================================");

                                int index = 0;
                                arr = new ArrayList<>();
                                for (MainDTO real : data) {

                                    Log.d(TAG, "real: " + real.toString());

                                    if(data.size() - 1 == index) {
                                        name = real.getName();
                                        userName.setText(name);
                                        MainUserDataMediator.setAge(real.getAge());
                                        MainUserDataMediator.setGender(real.getGender());
                                        rAdapter.setList(arr);
                                        break;
                                    }

                                    shop = real.getShop();
                                    surl = real.getSurl();
                                    Log.d(TAG, "shop" + index + ": " + shop + "\nsurl" + index + ": " + surl);
                                    arr.add(new RecommendShopListItem(String.valueOf(index + 1), shop, surl));
                                    // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

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
        recommendRecyclerView = view.findViewById(R.id.recommendRecyclerView);
    }
}
