package com.example.fybproject.service;

import com.example.fybproject.dto.authDTO.PlusInfoDTO;
import com.example.fybproject.dto.authDTO.CheckDTO;
import com.example.fybproject.dto.authDTO.DeleteDTO;
import com.example.fybproject.dto.authDTO.LoginDTO;
import com.example.fybproject.dto.authDTO.LogoutDTO;
import com.example.fybproject.dto.authDTO.ProfileImgDTO;
import com.example.fybproject.dto.authDTO.PwChangeDTO;
import com.example.fybproject.dto.authDTO.RegisterDTO;
import com.example.fybproject.dto.authDTO.SocialDTO;

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

public interface AuthService {

    // 회원가입
    //@Headers("Content-Type: application/json")
    @POST("auth")
    Call<RegisterDTO> postRegisterData(@Body RegisterDTO registerDTO);

    // 로그인
    @POST("auth/log")
    Call<LoginDTO> postLoginData(@Body LoginDTO loginDTO);

    // 비밀번호 변경
    @PATCH("auth/password")
    Call<PwChangeDTO> patchPasswordData(@Body PwChangeDTO pwChangeDTO);

    // 비밀번호 잃어버렸을 때 변경
    @PUT("auth/password")
    Call<PwChangeDTO> putLostPasswordData(@Body PwChangeDTO pwChangeDTO);

    // 회원탈퇴
    @HTTP(method = "DELETE", path = "auth/", hasBody = true)
    Call<DeleteDTO> deleteUserData(@Body DeleteDTO deleteDTO);

    // 로그아웃
    @DELETE("auth/logout")
    Call<LogoutDTO> deleteLogoutData();

    // 휴대폰 인증
    @POST("auth/check")
    Call<CheckDTO> postCheckData(@Body CheckDTO checkDTO);

    // 구글 로그인
    @GET("auth/google")
    Call<SocialDTO> getGoogleData();

    // 카카오 로그인
    @GET("auth/kakao")
    Call<SocialDTO> getKakaoData();

    // 소셜 로그인 이후 추가 정보 요청
    @PUT("auth")
    Call<PlusInfoDTO> putPlusInfoData(@Body PlusInfoDTO plusInfoDTO);

    // 프로필 이미지 설정
    @Multipart
    @PUT("auth/image")
    Call<ProfileImgDTO> putImgurlData(@Part MultipartBody.Part file);
}
