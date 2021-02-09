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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.eventmap.R;
import com.example.android.eventmap.util.MySharedPreferences;
import com.google.android.material.navigation.NavigationView;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    MapView mapView;
    NaverMap mNaverMap;
    DrawerLayout main_drawerLayout;
    ImageButton ibtn_navigationOpen;
    NavigationView navigationview_setting;
    View headerView;

    LinearLayout ll_basic, ll_satellite, ll_terrain, ll_traffic, ll_transit, ll_bicycle, ll_mountain, ll_cadastral, ll_indoor;
    TextView tv_basic, tv_hybrid, tv_terrain, tv_traffic, tv_transit, tv_bicycle, tv_mountain, tv_cadastral, tv_indoor;
    ImageView iv_basic, iv_satellite, iv_terrain;
    int[] click_count = new int[6];

    MySharedPreferences mySharedPreferences;

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
        tv_hybrid = headerView.findViewById(R.id.tv_hybrid);
        tv_terrain = headerView.findViewById(R.id.tv_terrain);
        iv_basic = headerView.findViewById(R.id.iv_basic);
        iv_basic.setClipToOutline(true);
        iv_satellite = headerView.findViewById(R.id.iv_satellite);
        iv_satellite.setClipToOutline(true);
        iv_terrain = headerView.findViewById(R.id.iv_terrain);
        iv_terrain.setClipToOutline(true);
        ll_indoor = headerView.findViewById(R.id.ll_indoor);
        ll_traffic = headerView.findViewById(R.id.ll_traffic);
        ll_transit = headerView.findViewById(R.id.ll_transit);
        ll_bicycle = headerView.findViewById(R.id.ll_bicycle);
        ll_mountain = headerView.findViewById(R.id.ll_mountain);
        ll_cadastral = headerView.findViewById(R.id.ll_cadastral);
        tv_indoor = headerView.findViewById(R.id.tv_indoor);
        tv_traffic = headerView.findViewById(R.id.tv_traffic);
        tv_transit = headerView.findViewById(R.id.tv_transit);
        tv_bicycle = headerView.findViewById(R.id.tv_bicycle);
        tv_mountain = headerView.findViewById(R.id.tv_mountain);
        tv_cadastral = headerView.findViewById(R.id.tv_cadastral);

        mySharedPreferences = new MySharedPreferences(this);
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

        //지도세팅 정보 불러오기
        calling_up_setting();
    }

    void calling_up_setting(){
        //지도종류 세팅
        if(mySharedPreferences.getBasic()){
            mNaverMap.setMapType(NaverMap.MapType.Basic);
            tv_basic.setTextColor(Color.BLACK);
        }else if(mySharedPreferences.getSatellite()){
            mNaverMap.setMapType(NaverMap.MapType.Hybrid);
            tv_hybrid.setTextColor(Color.BLACK);
        }else if(mySharedPreferences.getTerrain()){
            mNaverMap.setMapType(NaverMap.MapType.Terrain);
            tv_terrain.setTextColor(Color.BLACK);
        }

        //부가정보 세팅
        if(mySharedPreferences.getIndoor()){
            mNaverMap.setIndoorEnabled(true);
            tv_indoor.setTextColor(Color.BLUE);
            click_count[5]++;
        }
        if(mySharedPreferences.getTraffic()){
            mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRAFFIC, true);
            tv_traffic.setTextColor(Color.BLUE);
            click_count[0]++;
        }
        if(mySharedPreferences.getTransit()){
            mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);
            tv_transit.setTextColor(Color.BLUE);
            click_count[1]++;
        }
        if(mySharedPreferences.getBicycle()){
            mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BICYCLE, true);
            tv_bicycle.setTextColor(Color.BLUE);
            click_count[2]++;
        }
        if(mySharedPreferences.getMountain()){
            mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, true);
            tv_mountain.setTextColor(Color.BLUE);
            click_count[3]++;
        }
        if(mySharedPreferences.getCadastral()){
            mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_CADASTRAL, true);
            tv_cadastral.setTextColor(Color.BLUE);
            click_count[4]++;
        }
    }

    void ifClick(){
        ibtn_navigationOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_drawerLayout.openDrawer(GravityCompat.START);

                if(mNaverMap.getMapType() == NaverMap.MapType.Basic){
                    tv_basic.setTextColor(Color.BLACK);
                }else if(mNaverMap.getMapType() == NaverMap.MapType.Hybrid){
                    tv_hybrid.setTextColor(Color.BLACK);
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
                tv_hybrid.setTextColor(Color.GRAY);
                tv_terrain.setTextColor(Color.GRAY);

                mySharedPreferences.setBasic(true);
                mySharedPreferences.setSatellite(false);
                mySharedPreferences.setTerrain(false);
            }
        });
        ll_satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setMapType(NaverMap.MapType.Hybrid);

                tv_basic.setTextColor(Color.GRAY);
                tv_hybrid.setTextColor(Color.BLACK);
                tv_terrain.setTextColor(Color.GRAY);

                mySharedPreferences.setBasic(false);
                mySharedPreferences.setSatellite(true);
                mySharedPreferences.setTerrain(false);
            }
        });
        ll_terrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setMapType(NaverMap.MapType.Terrain);

                tv_basic.setTextColor(Color.GRAY);
                tv_hybrid.setTextColor(Color.GRAY);
                tv_terrain.setTextColor(Color.BLACK);

                mySharedPreferences.setBasic(false);
                mySharedPreferences.setSatellite(false);
                mySharedPreferences.setTerrain(true);
            }
        });

        ll_indoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click_count[5] == 0){
                    mNaverMap.setIndoorEnabled(true);
                    tv_indoor.setTextColor(Color.BLUE);
                    click_count[5]++;

                    mySharedPreferences.setIndoor(true);
                }else if(click_count[5] == 1){
                    mNaverMap.setIndoorEnabled(false);
                    tv_indoor.setTextColor(Color.GRAY);
                    click_count[5] = 0;

                    mySharedPreferences.setIndoor(false);
                }
            }
        });

        ll_traffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click_count[0] == 0){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRAFFIC, true);
                    tv_traffic.setTextColor(Color.BLUE);
                    click_count[0]++;

                    mySharedPreferences.setTraffic(true);
                }else if(click_count[0] == 1){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRAFFIC, false);
                    tv_traffic.setTextColor(Color.GRAY);
                    click_count[0] = 0;

                    mySharedPreferences.setTraffic(false);
                }
            }
        });
        ll_transit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click_count[1] == 0){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);
                    tv_transit.setTextColor(Color.BLUE);
                    click_count[1]++;

                    mySharedPreferences.setTransit(true);
                }else if(click_count[1] == 1){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, false);
                    tv_transit.setTextColor(Color.GRAY);
                    click_count[1] = 0;

                    mySharedPreferences.setTransit(false);
                }
            }
        });
        ll_bicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click_count[2] == 0){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BICYCLE, true);
                    tv_bicycle.setTextColor(Color.BLUE);
                    click_count[2]++;

                    mySharedPreferences.setBicycle(true);
                }else if(click_count[2] == 1){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BICYCLE, false);
                    tv_bicycle.setTextColor(Color.GRAY);
                    click_count[2] = 0;

                    mySharedPreferences.setBicycle(false);
                }
            }
        });
        ll_mountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click_count[3] == 0){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, true);
                    tv_mountain.setTextColor(Color.BLUE);
                    click_count[3]++;

                    mySharedPreferences.setMountain(true);
                }else if(click_count[3] == 1){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, false);
                    tv_mountain.setTextColor(Color.GRAY);
                    click_count[3] = 0;

                    mySharedPreferences.setMountain(false);
                }
            }
        });
        ll_cadastral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click_count[4] == 0){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_CADASTRAL, true);
                    tv_cadastral.setTextColor(Color.BLUE);
                    click_count[4]++;

                    mySharedPreferences.setCadastral(true);
                }else if(click_count[4] == 1){
                    mNaverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_CADASTRAL, false);
                    tv_cadastral.setTextColor(Color.GRAY);
                    click_count[4] = 0;

                    mySharedPreferences.setCadastral(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}