package com.example.fybproject.pager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.fybproject.R;

public class SignUpSecondFragment extends Fragment {

    View view;

    EditText inputRegisterAge, inputPhoneForRegister, inputCodeForRegister;

    Spinner heightSpinner, weightSpinner;

    ImageView receiveCodeBtnForRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  (ViewGroup) inflater.inflate(R.layout.fragment_sign_up_second, container, false);

        init();

        return view;
    }

    public void init() {
        inputRegisterAge = view.findViewById(R.id.inputRegisterAge);
        inputPhoneForRegister = view.findViewById(R.id.inputPhoneForRegister);
        inputCodeForRegister = view.findViewById(R.id.inputCodeForRegister);
        heightSpinner = view.findViewById(R.id.heightSpinner);
        weightSpinner = view.findViewById(R.id.weightSpinner);
        receiveCodeBtnForRegister = view.findViewById(R.id.receiveCodeBtn);
    }

    public void inputSecondPageData() {

    }
}
