package com.example.fybproject.pager.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fybproject.pager.fragment.SignUpFirstFragment;
import com.example.fybproject.pager.fragment.SignUpSecondFragment;

public class SignUpPagerAdapter extends FragmentStateAdapter {
    public int mCount;

    public SignUpPagerAdapter(FragmentActivity fa, int count) {
        super(fa);

        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index == 0) return new SignUpFirstFragment();
        else return new SignUpSecondFragment();
    }

    @Override
    public int getItemCount() {
        return 2; // 화면이 2000개
    }

    public int getRealPosition(int position) {
        return position % mCount;
    }
}
