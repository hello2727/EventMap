package com.example.android.eventmap.view;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.eventmap.R;
import com.example.android.eventmap.model.Body;
import com.example.android.eventmap.model.Interface.RetrofitInterface;
import com.example.android.eventmap.model.Items;
import com.example.android.eventmap.model.Response_J;
import com.example.android.eventmap.model.RetrofitClient;
import com.example.android.eventmap.util.MySharedPreferences;
import com.google.android.material.navigation.NavigationView;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.util.FusedLocationSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapView mapView;
    NaverMap mNaverMap;
    FusedLocationSource locationSource;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    DrawerLayout main_drawerLayout;
    ImageButton ibtn_navigationOpen;
    NavigationView navigationview_setting;
    View headerView;
    long backBtnTime = 0;

    LinearLayout ll_basic, ll_satellite, ll_terrain, ll_navi, ll_traffic, ll_transit, ll_bicycle, ll_mountain, ll_cadastral, ll_indoor;
    TextView tv_basic, tv_hybrid, tv_terrain, tv_navi, tv_traffic, tv_transit, tv_bicycle, tv_mountain, tv_cadastral, tv_indoor;
    ImageView iv_basic, iv_satellite, iv_terrain, iv_navi;
    int[] click_count = new int[6];

    MySharedPreferences mySharedPreferences;

    RetrofitClient retrofitClient;
    RetrofitInterface retrofitInterface;
    List<Items> items; //축제정보 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //초기화하기
        init();
        //NaverMap 객체 얻어오기
        getMapInstance();
        //옵션 클릭하기
        ifClick();
        //축제정보 api 가져오기
        getEventAPI();
    }

    void init() {
        mapView = findViewById(R.id.mapView);
        main_drawerLayout = findViewById(R.id.main_drawerLayout);
        ibtn_navigationOpen = findViewById(R.id.ibtn_navigationOpen);
        navigationview_setting = findViewById(R.id.navigationview_setting);
        headerView = navigationview_setting.getHeaderView(0);

        ll_basic = headerView.findViewById(R.id.ll_basic);
        ll_satellite = headerView.findViewById(R.id.ll_satellite);
        ll_terrain = headerView.findViewById(R.id.ll_terrain);
        ll_navi = headerView.findViewById(R.id.ll_navi);
        tv_basic = headerView.findViewById(R.id.tv_basic);
        tv_hybrid = headerView.findViewById(R.id.tv_hybrid);
        tv_terrain = headerView.findViewById(R.id.tv_terrain);
        tv_navi = headerView.findViewById(R.id.tv_navi);
        iv_basic = headerView.findViewById(R.id.iv_basic);
        iv_basic.setClipToOutline(true);
        iv_satellite = headerView.findViewById(R.id.iv_satellite);
        iv_satellite.setClipToOutline(true);
        iv_terrain = headerView.findViewById(R.id.iv_terrain);
        iv_terrain.setClipToOutline(true);
        iv_navi = headerView.findViewById(R.id.iv_navi);
        iv_navi.setClipToOutline(true);
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

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { //권한 거부됨
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        //야간모드 설정하기(낮에는 밝은지도, 밤에는 어두운 지도) -> 내비게이션 지도에만 적용됨.
        setNightMode();
        //UI 이벤트 설정하기
        setUiEvent();
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
        }else if(mySharedPreferences.getNavi()){
            mNaverMap.setMapType(NaverMap.MapType.Navi);
            tv_navi.setTextColor(Color.BLACK);
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

    void setNightMode(){
        long curTime = System.currentTimeMillis();;
        Date date = new Date(curTime);
        SimpleDateFormat format = new SimpleDateFormat("HH");
        String curHour = format.format(date);
        int hour = Integer.parseInt(curHour);

        Log.d("현재시간", Integer.toString(hour));

        if(06 < hour && hour < 21){
            mNaverMap.setNightModeEnabled(false);
        }else{
            mNaverMap.setNightModeEnabled(true);
        }
    }

    void setUiEvent(){
        UiSettings uiSettings = mNaverMap.getUiSettings();
        //현위치버튼(위치추적)
        uiSettings.setLocationButtonEnabled(true);
        mNaverMap.setLocationSource(locationSource);
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        //위치트래킹 기능 on
        mNaverMap.addOnOptionChangeListener(() -> {
            LocationTrackingMode mode = mNaverMap.getLocationTrackingMode();
            locationSource.setCompassEnabled(mode == LocationTrackingMode.Follow || mode == LocationTrackingMode.Face);
        });
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
                }else if(mNaverMap.getMapType() == NaverMap.MapType.Navi){
                    tv_navi.setTextColor(Color.BLACK);
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
                tv_navi.setTextColor(Color.GRAY);

                mySharedPreferences.setBasic(true);
                mySharedPreferences.setSatellite(false);
                mySharedPreferences.setTerrain(false);
                mySharedPreferences.setNavi(false);
            }
        });
        ll_satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setMapType(NaverMap.MapType.Hybrid);

                tv_basic.setTextColor(Color.GRAY);
                tv_hybrid.setTextColor(Color.BLACK);
                tv_terrain.setTextColor(Color.GRAY);
                tv_navi.setTextColor(Color.GRAY);

                mySharedPreferences.setBasic(false);
                mySharedPreferences.setSatellite(true);
                mySharedPreferences.setTerrain(false);
                mySharedPreferences.setNavi(false);
            }
        });
        ll_terrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setMapType(NaverMap.MapType.Terrain);

                tv_basic.setTextColor(Color.GRAY);
                tv_hybrid.setTextColor(Color.GRAY);
                tv_terrain.setTextColor(Color.BLACK);
                tv_navi.setTextColor(Color.GRAY);

                mySharedPreferences.setBasic(false);
                mySharedPreferences.setSatellite(false);
                mySharedPreferences.setTerrain(true);
                mySharedPreferences.setNavi(false);
            }
        });
        ll_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setMapType(NaverMap.MapType.Navi);

                tv_basic.setTextColor(Color.GRAY);
                tv_hybrid.setTextColor(Color.GRAY);
                tv_terrain.setTextColor(Color.GRAY);
                tv_navi.setTextColor(Color.BLACK);

                mySharedPreferences.setBasic(false);
                mySharedPreferences.setSatellite(false);
                mySharedPreferences.setTerrain(false);
                mySharedPreferences.setNavi(true);
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

    void getEventAPI(){
        long curTime = System.currentTimeMillis();;
        Date date = new Date(curTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        String curDay = format.format(date);

        retrofitInterface.getEvent(0, 100, "json").enqueue(new Callback<Response_J>() {
            @Override
            public void onResponse(Call<Response_J> call, Response<Response_J> response) {
                Response_J response_j = response.body();
                //                Gson gson = new Gson();
//                Body body = gson.fromJson(response_j.toString(), Body.class);
                Body body = response_j.getBody();
                items = body.getItems();
                Log.d("retrofit", "Data fetch success");
                for(int i = 0; i < items.size(); i++) {
                    Log.d("출력 내용", items.get(i).toString());
                }
            }

            @Override
            public void onFailure(Call<Response_J> call, Throwable t) {
                Log.d("retrofit 오류", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(main_drawerLayout.isDrawerOpen(navigationview_setting)){
            main_drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            //두번 눌러 뒤로가기 종료
            if(0 <= gapTime && 2000 >= gapTime){
                super.onBackPressed();
            }else{
                backBtnTime = curTime;
                Toast.makeText(this, "버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}