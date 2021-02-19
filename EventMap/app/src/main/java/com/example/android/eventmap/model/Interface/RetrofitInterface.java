package com.example.android.eventmap.model.Interface;

import com.example.android.eventmap.model.Response_J;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("openapi/tn_pubr_public_cltur_fstvl_api?serviceKey=flqNpXIymv5sUe63nK6VOIcPpe4Gjh3ms%2FuIsRg9nYrtrsoRAzNWiBCGzxczHzbgNa0PSOgF3ROfJZYDaqybfA%3D%3D&pageNo=0&numOfRows=100&type=json")
    Call<Response_J> getEvent();
}
