package com.example.fybproject.interceptor;

public class JwtToken {

    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        JwtToken.token = token;
    }
}
