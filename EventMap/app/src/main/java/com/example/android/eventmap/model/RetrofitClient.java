package com.example.android.eventmap.model;

import com.example.android.eventmap.model.Interface.RetrofitInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static RetrofitInterface retrofitInterface;
    private static String BASE_URL = "https://data.go.kr/index.do";

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public static RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
    }
}
