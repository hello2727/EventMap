개인 프로젝트
==============================
>전국 여행정보 알림 어플리케이션

개발환경
-----------------
* Android Java NaverMap_API SharedPreferences restAPI Retrofit2 GSON

결과물
-----------------
### 전체적인 구성
- 로딩 화면

- 위치추적 권한 허용 선택창
<div>
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108025252-80d12800-7069-11eb-8ef3-8f369e0e2bf3.jpg">
</div>

- 1.권한 허용 후 현재위치 표시  2.다른 장소보다가 위치추적 버튼 누르면 현재 위치 표시하기/한번 더 누르면 움직이는 방향에 따라 지도와 화살표 움직이기
<div>
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108025361-af4f0300-7069-11eb-96e2-166e6909f38b.jpg">
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108027220-28038e80-706d-11eb-9539-65cf9b327075.gif">
</div>
  
환경설정
-----------------
- build.gradle(module)
```
dependencies {
  ...
    // 네이버 지도 SDK
    implementation 'com.naver.maps:map-sdk:3.10.2'
    //내장 위치 추적 기능 (FusedLocation)
    implementation 'com.google.android.gms:play-services-location:16.0.0'
    // openAPI 정보 얻어오기 (retrofit2 + gson)
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.0'
}
```
- build.gradle(Project)
```
allprojects {
        ...
        maven {
            url 'https://naver.jfrog.io/artifactory/maven/'
        }
}
```
- manifest
```
...
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />

<application
  android:usesCleartextTraffic="true"
  android:allowBackup="false"
  <meta-data
        android:name="com.naver.maps.map.CLIENT_ID"
        android:value="{값}"/>
</application>        
```
