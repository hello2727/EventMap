package com.example.android.eventmap.model;

import com.example.android.eventmap.model.remote.net.MainNetwork;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static MainNetwork retrofitInterface;
    private static String BASE_URL = "http://api.data.go.kr/openapi/";

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(MainNetwork.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static MainNetwork getRetrofitInterface() {
        return retrofitInterface;
    }
}
