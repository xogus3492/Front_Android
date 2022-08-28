package com.example.fybproject.service;

import com.example.fybproject.dto.myClosetDTO.ClosetAddDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetDeleteDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetUpdateDTO;
import com.example.fybproject.dto.wishlistDTO.WishAddDTO;
import com.example.fybproject.dto.wishlistDTO.WishDeleteDTO;
import com.example.fybproject.dto.wishlistDTO.WishUpdateDTO;
import com.example.fybproject.dto.wishlistDTO.WishlistDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface MyClosetService {
    // 내 옷장 등록
    @POST() // 미완성
    Call<ClosetAddDTO> postClosetAddData(@Body ClosetAddDTO closetAddDTO);

    // 내 옷장 조회
    @GET("main/closet/read")
    Call<ClosetDTO> getClosetData();

    // 내 옷장 삭제
    @POST() // 미완성
    Call<ClosetDeleteDTO> postClosetDeleteData(@Body ClosetDeleteDTO closetDeleteDTO);

    // 내 옷장 수정
    @PATCH() //  미완성
    Call<ClosetUpdateDTO> patchClosetUpdateData(@Body ClosetUpdateDTO closetUpdateDTO);
}
