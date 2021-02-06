package com.example.ifoundit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.naver.maps.map.NaverMapSdk;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NaverMap 객체 얻어오기
        getMapInstance();

    }

    void getMapInstance(){
        //인증 실패 처리
        NaverMapSdk.getInstance(this).setOnAuthFailedListener(new NaverMapSdk.OnAuthFailedListener() {
            @Override
            public void onAuthFailed(@NonNull NaverMapSdk.AuthFailedException e) {
                e.printStackTrace();
            }
        });
    }
}
