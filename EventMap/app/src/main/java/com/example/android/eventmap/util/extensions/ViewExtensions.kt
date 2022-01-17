package com.example.android.eventmap.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

/**
 * Created by Jihye Noh
 * Date: 2022-01-17
 */
fun View.clipCornerToRound(radius: Float) {
    this.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }
}
