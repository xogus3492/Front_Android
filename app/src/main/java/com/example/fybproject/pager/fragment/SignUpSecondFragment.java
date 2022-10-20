package com.example.fybproject.pager.fragment;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.fybproject.DataMediator;
import com.example.fybproject.R;

public class SignUpSecondFragment extends Fragment {
    View view;

    EditText inputRegisterAge, inputRegisterHeight, inputRegisterWeight;
    RadioGroup genderRG;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_second, container, false);

        init();

        inputRegisterAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "age : " + s);
                DataMediator.setAge(Integer.parseInt(s.toString()));
            }
        }); // user age

        inputRegisterHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "height : " + s);
                DataMediator.setHeight(Integer.parseInt(s.toString()));
            }
        }); //user height

        inputRegisterWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "weight : " + s);
                DataMediator.setWeight(Integer.parseInt(s.toString()));
            }
        }); // user weight

        genderRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.maleBtn:
                        Log.d(TAG, "gender : 남자");
                        DataMediator.setGender("M");
                        break;
                    case R.id.femaleBtn:
                        Log.d(TAG, "gender : 여자");
                        DataMediator.setGender("W");
                        break;
                }
            }
        }); // user gender

        return view;
    }

    public void init() {
        inputRegisterAge = view.findViewById(R.id.inputRegisterAge);
        genderRG = view.findViewById(R.id.genderRG);
        inputRegisterHeight = view.findViewById(R.id.inputRegisterHeight);
        inputRegisterWeight = view.findViewById(R.id.inputRegisterWeight);
    }
}
