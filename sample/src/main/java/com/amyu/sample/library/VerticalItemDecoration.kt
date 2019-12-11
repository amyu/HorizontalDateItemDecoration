package com.amyu.sample.library

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView


class VerticalItemDecoration(
    private val _context: Context,
    private val texts: List<String>
) : RecyclerView.ItemDecoration(), ViewExtensions {
    private val backgroundPaint = Paint().apply { color = Color.parseColor("#30123456") }
    private val textPaint = Paint().apply {
        color = Color.parseColor("#FF00FF")
        textSize = 14f.sp
        isAntiAlias = true
    }

    private val width = 40.dp

    private val percentY = 1f / (texts.size + 1)

    override fun getContext(): Context = _context

    override fun onDrawOver(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val canDrawRect = parent.children.firstOrNull()?.let {
            RectF(0f, it.y, width.toFloat(), canvas.height.toFloat())
        } ?: return

        //todo 横軸の線を引く
        canvas.drawRect(canDrawRect, backgroundPaint)

        texts.forEachIndexed { index, text ->
            val textSizeRect = getTextSizeRect(textPaint, text)
            val textX = (canDrawRect.width() - textSizeRect.width()) / 2
            val textY =
                textSizeRect.height() / 2 + (index + 1) * percentY * canDrawRect.height() + canDrawRect.top
            canvas.drawText(text, textX, textY, textPaint)
        }
    }
}