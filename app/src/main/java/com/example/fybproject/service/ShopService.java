package com.example.fybproject.service;

import com.example.fybproject.dto.authDTO.LoginDTO;
import com.example.fybproject.dto.shopDTO.AnalyzeDTO;
import com.example.fybproject.dto.shopDTO.FilterDTO;
import com.example.fybproject.dto.shopDTO.MainDTO;
import com.example.fybproject.dto.shopDTO.SearchDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ShopService {
    // 메인
    @GET("main")
    Call<ArrayList<MainDTO>> getMainData();

    // 검색 (load)
    @GET("main/search")
    Call<ArrayList<SearchDTO>> getSearchData();

    // 검색 (search)
    @POST("main/search")
    Call<ArrayList<SearchDTO>> postSearchData(@Body SearchDTO searchDTO);

    // 쇼핑몰 이용 분석
    @POST("main")
    Call<AnalyzeDTO> postAnalyzeData(@Body AnalyzeDTO analyzeDTO);

    // 쇼핑몰 최다 조회수
    @GET("main")
    Call<ArrayList<FilterDTO>> getMostViews();

    // 쇼핑몰 나이대별 조회 수
    @POST("main")
    Call<ArrayList<FilterDTO>> postViewByAge(@Body FilterDTO filterDTO);

    // 쇼핑몰 성별 조회 수
    @POST("main")
    Call<ArrayList<FilterDTO>> postViewByGender(@Body FilterDTO filterDTO);
}
