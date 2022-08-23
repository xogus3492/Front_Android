package com.example.fybproject.client;

import com.example.fybproject.service.AuthService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Client instance = null;
    private static AuthService authService;
    private final static String BASE_URL = "http://10.0.2.2:8080/";


    private Client(int n) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if(n == 1) {
            authService = retrofit.create(AuthService.class);
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