package com.example.fybproject.service;

import com.example.fybproject.dto.authDTO.CheckDTO;
import com.example.fybproject.dto.authDTO.DeleteDTO;
import com.example.fybproject.dto.authDTO.LoginDTO;
import com.example.fybproject.dto.authDTO.LogoutDTO;
import com.example.fybproject.dto.authDTO.PwChangeDTO;
import com.example.fybproject.dto.authDTO.RegisterDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface AuthService {

    // 회원가입
    //@Headers("Content-Type: application/json")
    @POST("auth/register")
    Call<RegisterDTO> postRegisterData(@Body RegisterDTO registerDTO);

    // 로그인
    @POST("auth/login")
    Call<LoginDTO> postLoginData(@Body LoginDTO loginDTO);

    // 비밀번호 변경
    @PATCH("auth/pwchange")
    Call<PwChangeDTO> postPwChangeData(@Body PwChangeDTO pwChangeDTO);

    // 비밀번호 잃어버렸을 때 변경
    @POST("auth/lost/pwchange")
    Call<PwChangeDTO> postLostPwChangeData(@Body PwChangeDTO pwChangeDTO);

    // 회원탈퇴
    @POST("auth/delete")
    Call<DeleteDTO> postDeleteData(@Body DeleteDTO deleteDTO);

    // 로그아웃
    @POST("auth/logout")
    Call<LogoutDTO> postLogoutData(@Body LogoutDTO logoutDTO);

    // 휴대폰 인증
    @POST("auth/check")
    Call<CheckDTO> postCheckData(@Body CheckDTO checkDTO);
}
