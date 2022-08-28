package com.example.fybproject.client;

import com.example.fybproject.service.AuthService;
import com.example.fybproject.service.InfoService;
import com.example.fybproject.service.MyClosetService;
import com.example.fybproject.service.ShopService;
import com.example.fybproject.service.WishlistService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Client instance = null;
    private static AuthService authService;
    private static ShopService shopService;
    private static InfoService infoService;
    private static WishlistService wishlistService;
    private static MyClosetService myClosetService;
    private final static String BASE_URL = "http://10.0.2.2:8080/";


    private Client(int n) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if(n == 1) {
            authService = retrofit.create(AuthService.class);
        }
        if(n == 2) {
            shopService = retrofit.create(ShopService.class);
        }
        if(n == 3) {
            infoService = retrofit.create(InfoService.class);
        }
        if(n == 4) {
            wishlistService = retrofit.create(WishlistService.class);
        }
        if(n == 5) {
            myClosetService = retrofit.create(MyClosetService.class);
        }
    }

    public static Client getInstance(int n) {
        if (instance == null) {
            instance = new Client(n);
        }

        return instance;
    }

    public static AuthService getAuthService() {
        return authService;
    }
}