개인 프로젝트
==============================
>전국 여행정보 알림 어플리케이션

앱스토어 등록여부
-----------------
* 
* 주소: 

개발환경(Used Tools)
-----------------
* Android | Language => Java Kotline | Postman | Map Sdk => NaverMap_API | restAPI => Retrofit2 GSON | clean Code | Clean Architecture | DI => Hilt Dagger | Coroutine Flow LiveData | LeakCanary | Log => Timber | Image Processing => Glide | View Binding => Data binding, View binding | 

진행상황
-----------------
현재 open API를 제공하는 서버측의 JSON 처리 문제로 개발에 지연이 있는 상황입니다. 

결과물
-----------------
### 전체적인 구성
- 로딩 화면

- 위치추적 권한 허용 선택창
<div>
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108025252-80d12800-7069-11eb-8ef3-8f369e0e2bf3.jpg">
</div>

- 1.권한 허용 후 현재위치 표시  2.다른 장소보다가 위치추적 버튼 누르면 현재 위치 표시하기/한번 더 누르면 움직이는 방향에 맞추어 지도와 화살표 움직이기
<div>
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108025361-af4f0300-7069-11eb-96e2-166e6909f38b.jpg">
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108027220-28038e80-706d-11eb-9539-65cf9b327075.gif">
</div>

- 지도에 옵션 적용 (1.일반지도, 위성지도, 지형도, 내비게이션지도(21시~6시 야간모드)  2.실내지도  3.교통정보, 대중교통, 자전거, 등산로, 지적편집도)
<div>
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108028514-4ff3f180-706f-11eb-9397-0584cd0972ad.jpg">
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108033589-8fbed700-7077-11eb-94a9-f9483545d700.gif">
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108033876-ff34c680-7077-11eb-9bee-1bfd260873bc.gif">
  <img width="200" src="https://user-images.githubusercontent.com/43267195/108034794-6606af80-7079-11eb-91e5-62f4591b1333.gif">
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
