package com.example.fybproject.main.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    ListView searchListView;

    SearchListItemAdapter adapter;

    private ShopService shopService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_search, container, false);

        init();

        adapter = new SearchListItemAdapter();

        SearchDTO searchDTO = new SearchDTO();
        shopService = ServiceGenerator.createService(ShopService.class);

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
        }

        /*loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                LoginDTO loginDTO = new LoginDTO(email, pw);
                authService = ServiceGenerator.createService(AuthService.class);

                if (authService != null) {
                    authService.postLoginData(loginDTO)
                            .enqueue(new Callback<LoginDTO>() {
                                @Override
                                public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                                    LoginDTO data = response.body();
                                    Headers header = response.headers();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "LocalLogin : 성공,\nresponseBody : " + data + ",\njwtToken : " + header.get("Authorization"));

                                        if (data.getStatus().equals("LOGIN_STATUS_TRUE")) {
                                            JwtToken.setToken(header.get("Authorization"));

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                        if (data.getStatus().equals("LOGIN_FALSE")) {
                                            Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호를 다시 확인해 주십시오", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        try {
                                            Log.d(TAG, "LocalLogin : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<LoginDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 로컬 로그인 */

        return view;
    }

    public void init() {
        searchListView = view.findViewById(R.id.searchListView);
    }

    /*public void inputData() {
        email = inputEmail.getText().toString();
        pw = inputPw.getText().toString();
    }*/
}
