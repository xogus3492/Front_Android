package com.example.fybproject.pager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.fybproject.R;

public class SignUpThirdFragment extends Fragment {
    View view;

    EditText inputRegisterAge, inputPhoneForRegister, inputCodeForRegister,
            inputRegisterHeight, inputRegisterWeight;
    ImageView receiveCodeBtnForRegister;

    int age, height, weight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_third, container, false);

        init();

        return view;
    }

    public void init() {

    }

    public void inputSecondPageData() {

    }
}
