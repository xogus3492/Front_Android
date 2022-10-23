package com.example.fybproject.mediator;

import static android.service.controls.ControlsProviderService.TAG;

import android.util.Log;

public class CartMediator {
    private static int price;

    public static int getPrice() {
        Log.d(TAG, "getPrice 확인, price : " + price);
        int p = price;
        price = 0;

        return p;
    }

    public static void setPrice(int price) {
        Log.d(TAG, "setprice 확인");
        CartMediator.price += price;
    }
}
