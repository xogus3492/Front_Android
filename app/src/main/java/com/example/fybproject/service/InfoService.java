package com.example.fybproject.service;

import com.example.fybproject.dto.infoDTO.EditDTO;
import com.example.fybproject.dto.infoDTO.InfoDTO;
import com.example.fybproject.dto.shopDTO.SearchDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface InfoService {
    // 내 정보 조회
    @GET("auth")
    Call<InfoDTO> getSearchData();

    // 내 정보 수정
    @PATCH("auth")
    Call<EditDTO> patchSearchData(@Body EditDTO editDTO);
}
