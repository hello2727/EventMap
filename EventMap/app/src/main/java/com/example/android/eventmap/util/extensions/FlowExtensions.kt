package com.example.android.eventmap.util.extensions

import kotlinx.coroutines.flow.Flow

/**
 * Created by Jihye Noh
 * Date: 2022-01-30
 */
fun <T1> Flow<T1>.toggle(): Boolean {
    return !this.equals(true)
}