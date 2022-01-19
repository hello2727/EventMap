package com.example.android.eventmap.repository.remote.vo

import com.google.gson.annotations.SerializedName

/**
 * Created by Jihye Noh
 * Date: 2022-01-14
 */
data class EventVo(
    @SerializedName("response")
    val event: String
)
