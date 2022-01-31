package com.example.android.eventmap.util.extensions

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Created by Jihye Noh
 * Date: 2022-01-31
 */
fun Activity.toastMessage(@StringRes text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}