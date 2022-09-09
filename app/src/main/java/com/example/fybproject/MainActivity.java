package com.example.fybproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fybproject.main.fragment.MainHomeFragment;

public class MainActivity extends AppCompatActivity {

    MainHomeFragment mainHomeFragment;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        mainHomeFragment = new MainHomeFragment();

        transaction = fragmentManager.beginTransaction();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameMainHome, mainHomeFragment).commitAllowingStateLoss();
    }
}