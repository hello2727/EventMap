# 프로젝트명 : 찾았지오
>내가 찾고자 하는 장소들을 검색하면 사용자 근처에서 찾아 알려주는 어플리케이션
## 1.결과물
- 로딩 화면
<div>
  <img width="200" src="https://user-images.githubusercontent.com/43267195/83619275-9f1ff200-a5c6-11ea-8164-d7e31303ac0b.jpg">
</div>

- 홈
<div>
  <img width="200" src="https://user-images.githubusercontent.com/43267195/83619354-b6f77600-a5c6-11ea-803c-3da4a4f00247.jpg">
  <img width="200" src="">
</div>
  
## 2.환경설정
### 2-1.build.gradle
```
dependencies {
  ...
  //네이버 맵 API
    implementation 'com.naver.maps:map-sdk:3.7.0'
    implementation 'com.google.android.gms:play-services-location:16.0.0'
    implementation 'com.github.arimorty:floatingsearchview:2.1.1'
}
```
### 2-2.build.gradle(Project)
```
allprojects {
        ...
        maven {
        //네이버 맵 API
            url 'https://navercorp.bintray.com/maps'
        }
}
```
### 2-3.manifest
```
...
<meta-data
            android:name="com.naver.maps.map.CLIENT_ID"
            android:value="{값}"/>
```
