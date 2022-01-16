package com.example.android.eventmap.model.remote.api

import com.example.android.eventmap.model.remote.vo.EventVo
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Jihye Noh
 * Date: 2022-01-14
 */
interface MainApi {
    @GET("tn_pubr_public_cltur_fstvl_api?serviceKey=flqNpXIymv5sUe63nK6VOIcPpe4Gjh3ms%2FuIsRg9nYrtrsoRAzNWiBCGzxczHzbgNa0PSOgF3ROfJZYDaqybfA%3D%3D&pageNo=0&numOfRows=100&type=json")
    fun getEvent(

    ): Call<EventVo>
}