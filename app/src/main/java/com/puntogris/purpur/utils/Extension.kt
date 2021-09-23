package com.puntogris.purpur.utils

import android.view.View

fun View.gone() {
    View.GONE.also { visibility = it }
}

fun View.visible() {
    visibility = View.VISIBLE
}