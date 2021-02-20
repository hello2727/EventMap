package com.example.android.eventmap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {
    @SerializedName("fstvlNm") //축제명
    @Expose
    private String fstvlNm;
    @SerializedName("opar") //개최장소
    @Expose
    private String opar;
    @SerializedName("fstvlStartDate") //축제시작일자
    @Expose
    private String fstvlStartDate;
    @SerializedName("fstvlEndDate") //축제종료일자
    @Expose
    private String fstvlEndDate;
    @SerializedName("fstvlCo") //축제내용
    @Expose
    private String fstvlCo;
    @SerializedName("mnnst") //주관기관
    @Expose
    private String mnnst;
    @SerializedName("auspcInstt") //주최기관
    @Expose
    private String auspcInstt;
    @SerializedName("suprtInstt") //후원기관
    @Expose
    private String suprtInstt;
    @SerializedName("phoneNumber") //전화번호
    @Expose
    private String phoneNumber;
    @SerializedName("homepageUrl") //홈페이지주소
    @Expose
    private String homepageUrl;
    @SerializedName("relateInfo") //관련정보
    @Expose
    private String relateInfo;
    @SerializedName("rdnmadr") //소재지도로명주소
    @Expose
    private String rdnmadr;
    @SerializedName("lnmadr") //소재지지번주소
    @Expose
    private String lnmadr;
    @SerializedName("latitude") //위도
    @Expose
    private String latitude;
    @SerializedName("longitude") //경도
    @Expose
    private String longitude;
    @SerializedName("referenceDate") //데이터기준일자
    @Expose
    private String referenceDate;
    @SerializedName("instt_code") //제공기관코드
    @Expose
    private String instt_code;
//    @SerializedName("instt_nm") //제공기관기관명
//    @Expose
//    private String instt_nm;
}
