package com.example.fybproject.main.fragment;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;

public class MainModelFragment extends Fragment {

    MainActivity mainactivity;

    VideoView model;
    SeekBar seekBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainactivity = (MainActivity)getActivity();
    } // 메인액티비티 객체 가져오기

    @Override
    public void onDetach() {
        super.onDetach();
        mainactivity=null;
    } // 메인액티비티 객체 해제

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main_model, container, false);

        //model = view.findViewById(R.id.videoView);
        //seekBar = view.findViewById(R.id.seekBar);

        //Log.d(TAG, "경로 : " + Uri.parse("android.resource://" + mainactivity.getPackageName() + "/" + R.raw.model1));

        //model.setVideoURI(Uri.parse("android.resource://" + mainactivity.getPackageName() + "/" + R.raw.model1));

        /*model.seekTo(model.getDuration() / 2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d(TAG, "currentPosition : " + model.getDuration() / 100 * i);
                model.seekTo(model.getDuration() / 100 * i);
                Log.d(TAG, "changePosition : " + i);
            } // 값이 변경될 때 호출

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            } // 처음 탭했을 때 호출

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            } // 탭하고 마지막에 뗄 때 호출
        });*/

        return view;
    }
}
