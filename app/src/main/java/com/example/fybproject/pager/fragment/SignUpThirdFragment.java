package com.example.fybproject.pager.fragment;

import static android.service.controls.ControlsProviderService.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.fybproject.mediator.RegisterDataMediator;
import com.example.fybproject.R;

public class SignUpThirdFragment extends Fragment {
    View view;

    RadioGroup formRG, sholderRG, pelvisRG, legRG;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_third, container, false);

        init();

        formRG.setOnCheckedChangeListener(rListener);
        sholderRG.setOnCheckedChangeListener(rListener);
        pelvisRG.setOnCheckedChangeListener(rListener);
        legRG.setOnCheckedChangeListener(rListener);

        return view;
    }

    RadioGroup.OnCheckedChangeListener rListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.upperBodyBtn:
                    Log.d(TAG, "form : 상체 비만");
                    RegisterDataMediator.setForm("1");
                    break;
                case R.id.lowerBodyBtn:
                    Log.d(TAG, "form : 하체 비만");
                    RegisterDataMediator.setForm("2");
                    break;
                case R.id.nomalBodyBtn:
                    Log.d(TAG, "form : 정상 체형");
                    RegisterDataMediator.setForm("3");
                    break;
                case R.id.sholderHighBtn:
                    Log.d(TAG, "sholder : 상");
                    RegisterDataMediator.setSholder("1");
                    break;
                case R.id.sholderMiddleBtn:
                    Log.d(TAG, "sholder : 중");
                    RegisterDataMediator.setSholder("2");
                    break;
                case R.id.sholderLowBtn:
                    Log.d(TAG, "sholder : 하");
                    RegisterDataMediator.setSholder("3");
                    break;
                case R.id.pelvisHighBtn:
                    Log.d(TAG, "pelvis : 중상");
                    RegisterDataMediator.setPelvis("1");
                    break;
                case R.id.pelvisLowBtn:
                    Log.d(TAG, "pelvis : 중하");
                    RegisterDataMediator.setPelvis("2");
                    break;
                case R.id.legHighBtn:
                    Log.d(TAG, "leg : 중상");
                    RegisterDataMediator.setLeg("1");
                    break;
                case R.id.legLowBtn:
                    Log.d(TAG, "leg : 중하");
                    RegisterDataMediator.setLeg("2");
                    break;
            }
        }
    };

    public void init() {
        formRG = view.findViewById(R.id.formRG);
        sholderRG = view.findViewById(R.id.sholderRG);
        pelvisRG = view.findViewById(R.id.pelvisRG);
        legRG = view.findViewById(R.id.legRG);
    }
}
