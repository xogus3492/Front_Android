package com.example.fybproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fybproject.client.ServiceGenerator;
import com.example.fybproject.dto.authDTO.ProfileImgDTO;
import com.example.fybproject.interceptor.JwtToken;
import com.example.fybproject.service.AuthService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileImageActivity extends AppCompatActivity {
    CircleImageView pImg;
    TextView pImgOk, pImgNo;

    private static final int REQUEST_CODE = 1001;
    private MultipartBody.Part body;

    private AuthService authService;

    // Storage Permissions
    /*private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);
        init();

        pImg.setOnClickListener(listener);
        pImgOk.setOnClickListener(listener);
        pImgNo.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pImg:
                    //verifyStoragePermissions(getParent());

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    //intent.setType("image/*");
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    //intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_CODE);
                case R.id.pImgOk:
                    Log.d(TAG, "확인1");
                    authService = ServiceGenerator.createService(AuthService.class, JwtToken.getToken());

                    if (authService != null) {
                        authService.putImgurlData(body)
                                .enqueue(new Callback<ProfileImgDTO>() {
                                    @Override
                                    public void onResponse(Call<ProfileImgDTO> call, Response<ProfileImgDTO> response) {
                                        ProfileImgDTO data = response.body();
                                        if (response.isSuccessful() == true) {
                                            Log.d(TAG, "pitPimgData : 성공,\nresponseBody : " + data);
                                            Log.d(TAG, "=====================================================================");

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            try {
                                                Log.d(TAG, "putPimgData : 실패,\nresponseBody() : " + data + ",\nresponse.code(): " + response.code() + ",\nresponse.errorBody(): " + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ProfileImgDTO> call, Throwable t) {
                                        Log.d(TAG, "onFailure: " + t.toString());
                                    }
                                });
                    }
                    break;
                case R.id.pImgNo:
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    break;
            }
        }
    };

    public void init() {
        pImg = findViewById(R.id.pImg);
        pImgOk = findViewById(R.id.pImgOk);
        pImgNo = findViewById(R.id.pImgNo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    pImg.setImageBitmap(img);

                    Log.d(TAG, "uri는?? " + data.getData());
                    Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null );
                    cursor.moveToNext();
                    @SuppressLint("Range") String absolutePath = cursor.getString( cursor.getColumnIndex( "_data" ) );
                    cursor.close();
                    Log.d(TAG, "절대경로 : " + absolutePath);
                    // 절대경로 얻기

                    /*Cursor c = context.getContentResolver().query(Uri.parse(uri.toString()), null,null,null,null);
                    c.moveToNext();
                    @SuppressLint("Range") String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA)); // 절대경로 얻기
                    Log.d(TAG, "절대경로 : " + absolutePath);*/

                    File f = new File(absolutePath);
                    Log.d(TAG, "file : " + f.toString());

                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), f);
                    Log.d(TAG, "requestBody : " + requestBody.toString());

                    body = MultipartBody.Part.createFormData("file", f.getName(), requestBody);
                    //Log.d(TAG, "body" + body.toString());
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }*/
}