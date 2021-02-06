package com.example.ifoundit;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //NaverMap 객체 얻어오기
        getMapInstance();

    }

    void init(){
        mapView = findViewById(R.id.mapView);
    }

    void getMapInstance(){
        //인증 실패 처리
        /*
         * UnauthorizedClientException(401):잘못된 클라이언트 ID를 지정함, 잘못된 클라이언트 유형을 사용함, 콘솔에서 앱 패키지 이름을 잘못 등록함
         * QuotaExceededException(429):콘솔에서 Maps 서비스를 선택하지 않음, 사용 한도가 초과됨
         * ClientUnspecifiedException(800):클라이언트 ID를 지정하지 않음
         * */
        NaverMapSdk.getInstance(this).setOnAuthFailedListener(new NaverMapSdk.OnAuthFailedListener() {
            @Override
            public void onAuthFailed(@NonNull NaverMapSdk.AuthFailedException e) {
                e.printStackTrace();
            }
        });

        //NaverMap 객체 얻어오기 (API 호출 가능) -> onMapReady 콜백 메서드 호출
        mapView.getMapAsync(this);
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

    }
}
