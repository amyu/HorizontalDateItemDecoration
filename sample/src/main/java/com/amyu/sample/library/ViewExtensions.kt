package com.amyu.sample.library

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect


internal interface ViewExtensions {
    fun getContext(): Context

    val Float.dp: Float
        get() = getContext().resources.displayMetrics.density * this@dp

    val Int.dp: Int
        get() = (getContext().resources.displayMetrics.density * this@dp).toInt()

    val Int.sp: Int
        get() = (getContext().resources.displayMetrics.scaledDensity * this@sp).toInt()

    val Float.sp: Float
        get() = getContext().resources.displayMetrics.scaledDensity * this@sp

    fun getTextSizeRect(
        paint: Paint,
        text: String
    ): Rect {
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        return bounds
    }
}