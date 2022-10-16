package com.example.fybproject.pager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.fybproject.R;

public class SignUpFirstFragment extends Fragment {

    View view;

    EditText inputRegisterEmail, inputRegisterPw, inputRegisterPwCheck, inputRegisterName;

    ImageView hideRegisterPw, showRegisterPw, hideRegisterPwCheck, showRegisterPwCheck;

    RadioGroup genderRG;

    RadioButton maleBtn, femailBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_first, container, false);

        init();

        return view;
    }

    public void init() {
        inputRegisterEmail = view.findViewById(R.id.inputRegisterEmail);
        inputRegisterPw = view.findViewById(R.id.inputRegisterPw);
        inputRegisterPwCheck = view.findViewById(R.id.inputRegisterPwCheck);
        inputRegisterName = view.findViewById(R.id.inputRegisterName);
        hideRegisterPw = view.findViewById(R.id.hideRegisterPw);
        showRegisterPw = view.findViewById(R.id.showRegisterPw);
        hideRegisterPwCheck = view.findViewById(R.id.hideRegisterPwCheck);
        showRegisterPwCheck = view.findViewById(R.id.showRegisterPwCheck);
        genderRG = view.findViewById(R.id.genderRG);
        maleBtn = view.findViewById(R.id.maleBtn);
        femailBtn = view.findViewById(R.id.femaleBtn);
    }

    public void inputFirstPageData() {

    }
}
