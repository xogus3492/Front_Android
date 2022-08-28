package com.example.fybproject.service;

import com.example.fybproject.dto.authDTO.LoginDTO;
import com.example.fybproject.dto.shopDTO.MainDTO;
import com.example.fybproject.dto.shopDTO.SearchDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ShopService {
    // 메인
    @GET("main")
    Call<MainDTO> getMainData(/* 배열 포맷 json */);

    // 검색 (load)
    @GET("main/search")
    Call<SearchDTO> getSearchData();

    // 검색 (search)
    @POST("main/search")
    Call<SearchDTO> postSearchData(@Body SearchDTO searchDTO);
}
