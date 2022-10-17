package com.example.fybproject.interceeptor;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Log.i(TAG, "AuthenticationIntercepter.java : " + authToken);

        Request.Builder builder = original.newBuilder()
                .addHeader("Authorization",authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}
