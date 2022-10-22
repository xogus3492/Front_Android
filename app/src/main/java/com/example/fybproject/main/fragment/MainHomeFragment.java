package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.service.controls.ControlsProviderService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fybproject.R;
import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.shopDTO.FilterDTO;
import com.example.fybproject.dto.shopDTO.MainDTO;
import com.example.fybproject.interceeptor.JwtToken;
import com.example.fybproject.listView.home.RecommendShopListItem;
import com.example.fybproject.listView.home.RecommendShopListItemAdapter;
import com.example.fybproject.listView.search.SearchListItem;
import com.example.fybproject.listView.search.SearchListItemAdapter;
import com.example.fybproject.mediator.MainUserDataMediator;
import com.example.fybproject.service.ShopService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainHomeFragment extends Fragment {
    View view;

    TextView userName;
    RadioGroup filterRG;

    private RecyclerView recommendRecyclerView, filterRecyclerView;
    private RecommendShopListItemAdapter rAdapter;
    private SearchListItemAdapter adapter;
    private ArrayList<RecommendShopListItem> arr;
    private ArrayList<SearchListItem> fArr;
    private Context mContext;

    String shop, surl;
    String name;

    private ShopService shopService, shopFilterService;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_home, container, false);

        init();
        loadRecommendShop();
        loadFilterShop();

        filterRG.setOnCheckedChangeListener(rListener);

        return view;
    }

    RadioGroup.OnCheckedChangeListener rListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            adapter = new SearchListItemAdapter();

            filterRecyclerView.setAdapter(adapter);
            filterRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

            shopFilterService = ServiceGenerator.createService(ShopService.class, JwtToken.getToken());

            switch (checkedId) {
                case R.id.countBtn:
                    Log.d(ControlsProviderService.TAG, "필터 : 조회 수");
                    if (shopFilterService != null) {
                        shopFilterService.getMostViews()
                                .enqueue(new Callback<ArrayList<FilterDTO>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<FilterDTO>> call, Response<ArrayList<FilterDTO>> response) {
                                        ArrayList<FilterDTO> data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(TAG, "getMostViews : 성공,\nresponseBody : " + data);
                                            Log.d(TAG, "=====================================================================");

                                            int index = 0;
                                            String shop, surl;
                                            fArr = new ArrayList<>();
                                            for (FilterDTO real : data) {
                                                Log.d(TAG, "real: " + real.toString());

                                                shop = real.getShop();
                                                surl = real.getSurl();
                                                Log.d(TAG, "shop" + index + ": " + shop + "\nsurl" + index + ": " + surl);
                                                fArr.add(new SearchListItem(shop, surl));
                                                // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                                if(data.size() - 1 == index) {
                                                    adapter.setList(fArr);
                                                    break;
                                                }

                                                index++;
                                            }
                                        } else {
                                            try {
                                                Log.d(TAG, "getMostViews : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ArrayList<FilterDTO>> call, Throwable t) {
                                        Log.d(TAG, "onFailure: " + t.toString());
                                    }
                                });
                    }
                    break;
                case R.id.ageBtn:
                    Log.d(ControlsProviderService.TAG, "필터 : 나이");
                    if (shopFilterService != null) {
                        shopFilterService.getViewByAge(MainUserDataMediator.getAge())
                                .enqueue(new Callback<ArrayList<FilterDTO>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<FilterDTO>> call, Response<ArrayList<FilterDTO>> response) {
                                        ArrayList<FilterDTO> data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(TAG, "getViewByAge : 성공,\nresponseBody : " + data);
                                            Log.d(TAG, "=====================================================================");

                                            int index = 0;
                                            String shop, surl;
                                            fArr = new ArrayList<>();
                                            for (FilterDTO real : data) {
                                                Log.d(TAG, "real: " + real.toString());

                                                shop = real.getShop();
                                                surl = real.getSurl();
                                                Log.d(TAG, "shop" + index + ": " + shop + "\nsurl" + index + ": " + surl);
                                                fArr.add(new SearchListItem(shop, surl));
                                                // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                                if(data.size() - 1 == index) {
                                                    adapter.setList(fArr);
                                                    break;
                                                }

                                                index++;
                                            }
                                        } else {
                                            try {
                                                Log.d(TAG, "getViewByAge : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ArrayList<FilterDTO>> call, Throwable t) {
                                        Log.d(TAG, "onFailure: " + t.toString());
                                    }
                                });
                    } // 쇼핑몰 조회
                    break;
                case R.id.genderBtn:
                    Log.d(ControlsProviderService.TAG, "필터 : 성별");
                    if (shopFilterService != null) {
                        shopFilterService.getViewByGender(MainUserDataMediator.getGender())
                                .enqueue(new Callback<ArrayList<FilterDTO>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<FilterDTO>> call, Response<ArrayList<FilterDTO>> response) {
                                        ArrayList<FilterDTO> data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(TAG, "getViewByAge : 성공,\nresponseBody : " + data);
                                            Log.d(TAG, "=====================================================================");

                                            int index = 0;
                                            String shop, surl;
                                            fArr = new ArrayList<>();
                                            for (FilterDTO real : data) {
                                                Log.d(TAG, "real: " + real.toString());

                                                shop = real.getShop();
                                                surl = real.getSurl();
                                                Log.d(TAG, "shop" + index + ": " + shop + "\nsurl" + index + ": " + surl);
                                                fArr.add(new SearchListItem(shop, surl));
                                                // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                                if(data.size() - 1 == index) {
                                                    adapter.setList(fArr);
                                                    break;
                                                }

                                                index++;
                                            }
                                        } else {
                                            try {
                                                Log.d(TAG, "getViewByAge : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ArrayList<FilterDTO>> call, Throwable t) {
                                        Log.d(TAG, "onFailure: " + t.toString());
                                    }
                                });
                    } // 쇼핑몰 조회
                    break;
            }
        }
    };

    public void init() {
        userName = view.findViewById(R.id.userName);
        recommendRecyclerView = view.findViewById(R.id.recommendRecyclerView);
        filterRecyclerView = view.findViewById(R.id.filterRecyclerView);
        filterRG = view.findViewById(R.id.filterRG);
    }

    public void loadRecommendShop() {
        rAdapter = new RecommendShopListItemAdapter();

        recommendRecyclerView.setAdapter(rAdapter);
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
    }

    public void loadFilterShop() {
        adapter = new SearchListItemAdapter();

        filterRecyclerView.setAdapter(adapter);
        filterRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));

        shopFilterService = ServiceGenerator.createService(ShopService.class, JwtToken.getToken());

        Log.d(ControlsProviderService.TAG, "필터 : 조회 수");
        if (shopFilterService != null) {
            shopFilterService.getMostViews()
                    .enqueue(new Callback<ArrayList<FilterDTO>>() {
                        @Override
                        public void onResponse(Call<ArrayList<FilterDTO>> call, Response<ArrayList<FilterDTO>> response) {
                            ArrayList<FilterDTO> data = response.body();
                            if (response.isSuccessful() == true) {
                                Log.d(TAG, "getMainData : 성공,\nresponseBody : " + data);
                                Log.d(TAG, "=====================================================================");

                                int index = 0;
                                String shop, surl;
                                fArr = new ArrayList<>();
                                for (FilterDTO real : data) {
                                    Log.d(TAG, "real: " + real.toString());

                                    shop = real.getShop();
                                    surl = real.getSurl();
                                    Log.d(TAG, "shop" + index + ": " + shop + "\nsurl" + index + ": " + surl);
                                    fArr.add(new SearchListItem(shop, surl));
                                    // for문에서 빠져 나가면 add한 내용이 없어지는 듯?

                                    if (data.size() - 1 == index) {
                                        adapter.setList(fArr);
                                        break;
                                    }

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
                        public void onFailure(Call<ArrayList<FilterDTO>> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.toString());
                        }
                    });
        }
    }
}
