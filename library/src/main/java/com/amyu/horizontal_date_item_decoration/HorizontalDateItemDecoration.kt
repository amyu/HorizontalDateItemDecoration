package com.amyu.horizontal_date_item_decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.TemporalAdjusters.firstDayOfMonth
import org.threeten.bp.temporal.TemporalAdjusters.lastDayOfMonth


abstract class HorizontalDateItemDecoration(
    private val _context: Context
) : RecyclerView.ItemDecoration(), ViewExtensions {
    private val height = 64.dp
    private val textSize = 14f.sp

    private val backgroundPaint = Paint().apply { color = Color.parseColor("#000000") }
    private val dayTextPaint = Paint().apply {
        color = Color.parseColor("#FF00FF")
        textSize = this@HorizontalDateItemDecoration.textSize
        isAntiAlias = true
    }
    private val yearAndMonthPaint = Paint().apply {
        color = Color.parseColor("#FFFF00")
        textSize = this@HorizontalDateItemDecoration.textSize
        isAntiAlias = true
    }

    override fun getContext(): Context = _context

    override fun onDraw(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), height.toFloat(), backgroundPaint)

        var isVisibleFirstDay = false
        var isVisibleLastDay = false
        var lastVisibleYearAndMonthText = ""
        var lastVisibleYearAndMonthY = 0f

        parent.children.forEach { view ->
            val adapterPosition = parent.getChildAdapterPosition(view)

            val localDateFromPosition = getDate(adapterPosition)

            //日付の描画
            val dayText = localDateFromPosition.dayOfMonth.toString()
            val dayTextSizeRect = getTextSizeRect(dayTextPaint, dayText)
            val dayTextX = view.x + (view.width - dayTextSizeRect.width()) / 2
            val dayTextY =
                dayTextSizeRect.height().toFloat() + (height / 2 - dayTextSizeRect.height()) / 2 + height / 2
            canvas.drawText(dayText, dayTextX, dayTextY, dayTextPaint)

            //年月の描画
            val yearAndMonthText =
                "${localDateFromPosition.year}年${localDateFromPosition.monthValue}月"
            val yearAndMonthTextSizeRect = getTextSizeRect(yearAndMonthPaint, yearAndMonthText)
            val yearAndMonthY =
                yearAndMonthTextSizeRect.height().toFloat() + (height / 2 - yearAndMonthTextSizeRect.height()) / 2

            val isFirstDayOfMonth =
                localDateFromPosition.isEqual(localDateFromPosition.with(firstDayOfMonth()))
            val isLastDayOfMonth =
                localDateFromPosition.isEqual(localDateFromPosition.with(lastDayOfMonth()))
            when {
                isFirstDayOfMonth -> {
                    (view.x).let { x ->
                        if (x > 0) {
                            canvas.drawText(yearAndMonthText, x, yearAndMonthY, yearAndMonthPaint)
                        } else {
                            canvas.drawText(yearAndMonthText, 0f, yearAndMonthY, yearAndMonthPaint)
                        }
                    }
                    isVisibleFirstDay = true
                }
                isLastDayOfMonth -> {
                    (view.x + view.width - yearAndMonthTextSizeRect.width()).let { x ->
                        if (x < 0) {
                            canvas.drawText(yearAndMonthText, x, yearAndMonthY, yearAndMonthPaint)
                        } else {
                            canvas.drawText(yearAndMonthText, 0f, yearAndMonthY, yearAndMonthPaint)
                        }
                    }
                    isVisibleLastDay = true
                }
            }

            lastVisibleYearAndMonthText = yearAndMonthText
            lastVisibleYearAndMonthY = yearAndMonthY
        }

        //年月の描画
        if (!isVisibleFirstDay && !isVisibleLastDay) {
            canvas.drawText(
                lastVisibleYearAndMonthText,
                0f,
                lastVisibleYearAndMonthY,
                yearAndMonthPaint
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(0, height, 0, 0)
    }

    abstract fun getDate(position: Int): LocalDate
}