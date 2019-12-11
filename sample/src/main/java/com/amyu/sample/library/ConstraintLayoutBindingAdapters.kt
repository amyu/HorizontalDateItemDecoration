package com.amyu.sample.library

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter


object ConstraintLayoutBindingAdapters {

    @JvmStatic
    @BindingAdapter("app:layout_constraintVertical_bias")
    fun setLayoutConstraintVerticalBias(
        view: View,
        percent: Float
    ) {
        val params = view.layoutParams as ConstraintLayout.LayoutParams
        params.verticalBias = percent
        view.layoutParams = params
    }
}