# 프로젝트명 : 찾았지오
>내가 찾고자 하는 장소들을 검색하면 사용자 근처에서 찾아 알려주는 어플리케이션
## 1.결과물
## 2.환경설정
### 2-1.build.gradle
```
dependencies {
  ...
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
