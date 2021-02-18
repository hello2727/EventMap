package com.example.android.eventmap.model.Interface;

import com.example.android.eventmap.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("?serviceKey=flqNpXIymv5sUe63nK6VOIcPpe4Gjh3ms%2FuIsRg9nYrtrsoRAzNWiBCGzxczHzbgNa0PSOgF3ROfJZYDaqybfA%3D%3D&pageNo=0&numOfRows=100&type=json")
    Call<Result> getEvent(@Query("fstvlNm") String fstvlNm, @Query("opar") String opar, @Query("fstvlStartDate") String fstvlStartDate, @Query("fstvlEndDate") String fstvlEndDate, @Query("fstvlCo") String fstvlCo, @Query("phoneNumber") String phoneNumber, @Query("rdnmadr") String rdnmadr, @Query("latitude") String latitude, @Query("longitude") String longitude);
}
