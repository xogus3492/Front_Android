package com.example.fybproject.service;

import com.example.fybproject.dto.wishlistDTO.WishAddDTO;
import com.example.fybproject.dto.wishlistDTO.WishDeleteDTO;
import com.example.fybproject.dto.wishlistDTO.WishUpdateDTO;
import com.example.fybproject.dto.wishlistDTO.WishlistDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface WishlistService {
    // 장바구니 등록
    @POST("main/wishlist/add")
    Call<WishAddDTO> postWishAddData(@Body WishAddDTO wishAddDTO);

    // 장바구니 조회
    @GET("main/wishlist")
    Call<WishlistDTO> getWishlistData();

    // 장바구니 삭제
    @POST("main/delete")
    Call<WishDeleteDTO> postWishDeleteData(@Body WishDeleteDTO wishDeleteDTO);

    // 장바구니 수정
    @PATCH("main/wishlist/update")
    Call<WishUpdateDTO> patchWishUpdateData(@Body WishUpdateDTO wishUpdateDTO);
}
