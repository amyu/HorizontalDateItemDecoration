package com.amyu.sample.library

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.amyu.sample.R
import com.amyu.sample.Weight
import org.threeten.bp.LocalDate


data class GraphItemViewModel(
    val weight: Float?,
    @ColorInt
    val backgroundColor: Int,
    //グラフ部分
    val startLine: Pair<Float, Float>,
    val startLineVisibility: Int,
    val endLine: Pair<Float, Float>,
    val endLineVisibility: Int,
    val circleY: Float,
    @DrawableRes
    val circleBackgroundRes: Int,
    val circleVisibility: Int,
    var width: Float = 99.0f
) {
    companion object {
        fun convert(
            weights: List<Weight>,
            maxWeight: Float,
            minWeight: Float
        ): List<GraphItemViewModel> =
            weights.mapIndexed { index, weightData ->
                convert(
                    currentWeightData = weightData,
                    beforeWeightData = weights.getOrNull(index + 1),
                    afterWeightData = weights.getOrNull(index - 1),
                    maxWeight = maxWeight,
                    minWeight = minWeight
                )
            }

        private fun convert(
            currentWeightData: Weight,
            beforeWeightData: Weight?,
            afterWeightData: Weight?,
            minWeight: Float,
            maxWeight: Float
        ): GraphItemViewModel {
            val circleY = 1 - ((currentWeightData.weight
                ?: 0f) - minWeight) / (maxWeight - minWeight)
            val beforeCircleY = 1 - ((beforeWeightData?.weight
                ?: 0f) - minWeight) / (maxWeight - minWeight)
            val afterCircleY = 1 - ((afterWeightData?.weight
                ?: 0f) - minWeight) / (maxWeight - minWeight)
            return GraphItemViewModel(
                weight = currentWeightData.weight,
                backgroundColor = when {
                    currentWeightData.date.isEqual(LocalDate.now()) -> "#F4DADE"
                    currentWeightData.date.toEpochDay() % 2 == 0L -> "#F7F9FC"
                    else -> "#FEFEFE"
                }.let(Color::parseColor),
                circleY = circleY,
                circleBackgroundRes = if (currentWeightData.date.isEqual(LocalDate.now())) {
                    R.drawable.today_circle
                } else {
                    R.drawable.circle
                },
                circleVisibility = if (currentWeightData.weight == null) {
                    View.GONE
                } else {
                    View.VISIBLE
                },
                startLine = (beforeCircleY + circleY) / 2f to circleY,
                endLine = circleY to (circleY + afterCircleY) / 2f,
                startLineVisibility = if (beforeWeightData?.weight == null) {
                    View.GONE
                } else {
                    View.VISIBLE
                },
                endLineVisibility = if (afterWeightData?.weight == null) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            )
        }
    }
}