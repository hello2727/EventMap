package com.example.android.eventmap.view;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.eventmap.R;
import com.google.android.material.navigation.NavigationView;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    MapView mapView;
    NaverMap mNaverMap;
    DrawerLayout main_drawerLayout;
    ImageButton ibtn_navigationOpen;
    NavigationView navigationview_setting;
    View headerView;

    LinearLayout ll_basic, ll_satellite, ll_terrain;
    TextView tv_basic, tv_satellite, tv_terrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //NaverMap 객체 얻어오기
        getMapInstance();

        ifClick();
    }

    void init(){
        mapView = findViewById(R.id.mapView);
        main_drawerLayout = findViewById(R.id.main_drawerLayout);
        ibtn_navigationOpen = findViewById(R.id.ibtn_navigationOpen);
        navigationview_setting = findViewById(R.id.navigationview_setting);
        headerView = navigationview_setting.getHeaderView(0);

        ll_basic = headerView.findViewById(R.id.ll_basic);
        ll_satellite = headerView.findViewById(R.id.ll_satellite);
        ll_terrain = headerView.findViewById(R.id.ll_terrain);
        tv_basic = headerView.findViewById(R.id.tv_basic);
        tv_satellite = headerView.findViewById(R.id.tv_satellite);
        tv_terrain = headerView.findViewById(R.id.tv_terrain);
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
        mNaverMap = naverMap;
    }

    void ifClick(){
        ibtn_navigationOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_drawerLayout.openDrawer(GravityCompat.START);

                if(mNaverMap.getMapType() == NaverMap.MapType.Basic){
                    tv_basic.setTextColor(Color.BLACK);
                }else if(mNaverMap.getMapType() == NaverMap.MapType.Satellite){
                    tv_satellite.setTextColor(Color.BLACK);
                }else if(mNaverMap.getMapType() == NaverMap.MapType.Terrain){
                    tv_terrain.setTextColor(Color.BLACK);
                }
            }
        });

        ll_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setMapType(NaverMap.MapType.Basic);

                tv_basic.setTextColor(Color.BLACK);
                tv_satellite.setTextColor(Color.GRAY);
                tv_terrain.setTextColor(Color.GRAY);
            }
        });
        ll_satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setMapType(NaverMap.MapType.Satellite);

                tv_basic.setTextColor(Color.GRAY);
                tv_satellite.setTextColor(Color.BLACK);
                tv_terrain.setTextColor(Color.GRAY);
            }
        });
        ll_terrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setMapType(NaverMap.MapType.Terrain);

                tv_basic.setTextColor(Color.GRAY);
                tv_satellite.setTextColor(Color.GRAY);
                tv_terrain.setTextColor(Color.BLACK);
            }
        });
    }
}