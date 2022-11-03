package com.example.fybproject.service;

import com.example.fybproject.dto.myClosetDTO.ClosetAddDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetDeleteDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetImgDTO;
import com.example.fybproject.dto.myClosetDTO.ClosetUpdateDTO;
import com.example.fybproject.dto.wishlistDTO.WishAddDTO;
import com.example.fybproject.dto.wishlistDTO.WishDeleteDTO;
import com.example.fybproject.dto.wishlistDTO.WishUpdateDTO;
import com.example.fybproject.dto.wishlistDTO.WishlistDTO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface MyClosetService {
    // 내 옷장 등록
    @POST("main/closet")
    Call<ArrayList<ClosetAddDTO>> postClosetData(@Body ClosetAddDTO closetAddDTO);

    // 장바구니 조회
    @GET("main/closet")
    Call<ArrayList<ClosetDTO>> getClosetData();

    // 장바구니 삭제
    @HTTP(method = "DELETE", path = "main/closet", hasBody = true)
    Call<ClosetDeleteDTO> deleteClosetData(@Body ClosetDeleteDTO closetDeleteDTO);

    // 장바구니 수정
    @PATCH("main/closet")
    Call<ClosetUpdateDTO> patchClosetData(@Body ClosetUpdateDTO closetUpdateDTO);

    // 이미지 업로드
    @Multipart
    @PUT("main/closet")
    Call<ClosetImgDTO> putClosetData(@Part("id") long id, @Part MultipartBody.Part file);
}
