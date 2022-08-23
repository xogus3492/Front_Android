package com.example.fybproject.service;

import com.example.fybproject.dto.LoginDTO;
import com.example.fybproject.dto.RegisterDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {

    // /register 등록
    //@Headers("Content-Type: application/json")
    @POST("register")
    Call<RegisterDTO> postRegisterData(@Body RegisterDTO registerDTO);

    // /login 여부
    @POST("login")
    Call<LoginDTO> postLoginData(@Body LoginDTO loginDTO);
}
