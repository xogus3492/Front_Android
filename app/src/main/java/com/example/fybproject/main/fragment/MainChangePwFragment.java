package com.example.fybproject.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fybproject.MainActivity;
import com.example.fybproject.R;

public class MainChangePwFragment extends Fragment {
    View view;

    EditText emailForChangePw, pwForChangePw, newPwForChangePw, phoneForChangePw, codeForChangePw;
    ImageView hideChangePw, showChangePw, hideChangeNewPw, showChangeNewPw
            ,receiveCodeForChangePw;

    MainActivity mainactivity;

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
        view = inflater.inflate(R.layout.fragment_change_pw, container, false);

        init();

        hideChangePw.setOnClickListener(eyeListener);
        showChangePw.setOnClickListener(eyeListener);
        hideChangeNewPw.setOnClickListener(eyeListener);
        showChangeNewPw.setOnClickListener(eyeListener);
        // 눈 아이콘 클릭

        return view;
    }

    View.OnClickListener eyeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.hideChangePw:
                    pwForChangePw.setInputType(InputType.TYPE_CLASS_TEXT);
                    hideChangePw.setVisibility(View.GONE);
                    showChangePw.setVisibility(View.VISIBLE);
                    break;
                case R.id.showChangePw:
                    pwForChangePw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showChangePw.setVisibility(View.GONE);
                    hideChangePw.setVisibility(View.VISIBLE);
                    break;
                case R.id.hideChangeNewPw:
                    newPwForChangePw.setInputType(InputType.TYPE_CLASS_TEXT);
                    hideChangeNewPw.setVisibility(View.GONE);
                    showChangeNewPw.setVisibility(View.VISIBLE);
                    break;
                case R.id.showChangeNewPw:
                    newPwForChangePw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showChangeNewPw.setVisibility(View.GONE);
                    hideChangeNewPw.setVisibility(View.VISIBLE);
                    break;
            }

        }
    };

    public void init() {
        emailForChangePw = view.findViewById(R.id.emailForChangePw);
        pwForChangePw = view.findViewById(R.id.pwForChangePw);
        newPwForChangePw = view.findViewById(R.id.newPwForChangePw);
        phoneForChangePw = view.findViewById(R.id.phoneForChangePw);
        codeForChangePw = view.findViewById(R.id.codeForChangePw);
        receiveCodeForChangePw = view.findViewById(R.id.receiveCodeForChangePw);
        hideChangePw = view.findViewById(R.id.hideChangePw);
        showChangePw = view.findViewById(R.id.showChangePw);
        hideChangeNewPw = view.findViewById(R.id.hideChangeNewPw);
        showChangeNewPw = view.findViewById(R.id.showChangeNewPw);
    }
}
