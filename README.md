개인 프로젝트
==============================
>전국 여행정보 알림 어플리케이션

개발환경
-----------------
* Android Java NaverMap_API SharedPreferences

결과물
-----------------
### 전체적인 구성
- 로딩 화면

- 홈
<div>
  <img width="200" src="https://user-images.githubusercontent.com/43267195/83619354-b6f77600-a5c6-11ea-803c-3da4a4f00247.jpg">
  <img width="200" src="https://user-images.githubusercontent.com/43267195/83619716-3be28f80-a5c7-11ea-8b53-c97140970015.jpg">
  <img width="200" src="https://user-images.githubusercontent.com/43267195/83619751-47ce5180-a5c7-11ea-9978-aafee1f1607d.jpg">
</div>
  
### 환경설정
-----------------
- build.gradle(module)
```
dependencies {
  ...
  // 네이버 지도 SDK
    implementation 'com.naver.maps:map-sdk:3.10.2'
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
<meta-data
            android:name="com.naver.maps.map.CLIENT_ID"
            android:value="{값}"/>
```
