package com.example.android.eventmap.util

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by Jihye Noh
 * Date: 2022-01-17
 */
@BindingAdapter("clipCornerToRound")
fun clipCornerToRound(layout: View, radius: Float) {
    layout.clipToOutline = true
    layout.clipCornerToRound(radius)
}