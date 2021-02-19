package com.example.android.eventmap.model.Interface;

import com.example.android.eventmap.model.Response_J;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("openapi/tn_pubr_public_cltur_fstvl_api")
    Call<Response_J> getEvent(@Query("serviceKey") String serviceKey, @Query("pageNo") int pageNo, @Query("numOfRows") int numOfRows, @Query("type") String type);
}
