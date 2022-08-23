package com.example.fybproject;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fybproject.client.Client;
import com.example.fybproject.dto.RegisterDTO;
import com.example.fybproject.service.AuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView edit_email, edit_password, edit_password2,
            edit_name, edit_age, register_btn, cancel_btn;
    RadioButton male_btn, female_btn;
    Spinner height_spinner, weight_spinner;
    // 컴포넌트

    String[] height_items = {"150", "160", "170", "180"}; //getResources().getStringArray(R.array.height_entries);
    String[] weight_items = {"50", "60", "70", "80"};
    // 스피너 아이템

    String email, pw, pw2, name, gender;
    int height, weight, age;
    // 회원가입 데이터

    private AuthService authService;
    private RegisterDTO registerDTO;
    // api 관련

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        connectAdapter();

        height_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                height = Integer.parseInt(height_items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }); // 키 스피너

        weight_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weight = Integer.parseInt(weight_items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }); // 몸무게 스피너

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                registerDTO = new RegisterDTO(name, email, pw);

                //Log.i(TAG, "name = " + registerPojo.getName() + ", email = " + registerPojo.getEmail()/* + ", pw = " + registerPojo.getPw()*/);

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                // 데이터 post
                Client client = Client.getInstance(1);

                if (client != null) {
                    authService = Client.getAuthService();
                    authService.postRegisterData(registerDTO)
                            .enqueue(new Callback<RegisterDTO>() {
                        @Override
                        public void onResponse(Call<RegisterDTO> call, Response<RegisterDTO> response) {
                            if(!response.isSuccessful()) {
                                try {
                                    Log.d(TAG, "onResponse: 실패, response.body(): " + response.body() + ", 응답코드: " + response.code() + ", response.errorBody(): " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            RegisterDTO data = response.body(); // json 파일 body 내용
                            Log.d(TAG, "onResponse: 성공, 결과\n" + data.toString());
                            Toast.makeText(getApplicationContext(), data.getName() + "님, " + data.getEmail() + "로 회원가입 성공하셨습니다.", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<RegisterDTO> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.toString());

                        }
                    });
                }

                finish();
            }
        }); // 회원가입 클릭

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        }); // 취소 클릭
    }

    public void init() {
        edit_email = findViewById(R.id.editEmail2);
        edit_password = findViewById(R.id.editPassword2);
        edit_password2 = findViewById(R.id.editPassword3);
        edit_name = findViewById(R.id.editName);
        male_btn = findViewById(R.id.maleBtn);
        female_btn = findViewById(R.id.femaleBtn);
        edit_age = findViewById(R.id.editAge);
        register_btn = findViewById(R.id.registerBtn);
        cancel_btn = findViewById(R.id.cancelBtn);
        height_spinner = (Spinner) findViewById(R.id.heightSpinner);
        weight_spinner = (Spinner) findViewById(R.id.weightSpinner);
    } // 컴포넌트 인스턴스화

    public void connectAdapter() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, height_items);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        height_spinner.setAdapter(adapter1);
        // 키 설정 스피너 어뎁터 연결

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weight_items);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        weight_spinner.setAdapter(adapter2);
        // 몸무게 설정 스피너 어뎁터 연결
    }

    public void inputData() {
        email = edit_email.getText().toString();
        pw = edit_password.getText().toString();
        name = edit_name.getText().toString();
        /*pw2 = edit_password2.getText().toString();
        age = Integer.parseInt(edit_age.getText().toString());

        if(male_btn.isChecked())
            gender = male_btn.getText().toString();
        else if(female_btn.isChecked())
            gender = male_btn.getText().toString();*/
    } // 데이터 저장
}