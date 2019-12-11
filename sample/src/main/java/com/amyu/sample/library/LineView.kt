package com.amyu.sample.library

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange


class LineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), ViewExtensions {
    private val paint = Paint().apply {
        color = Color.parseColor("#F9596F")
        strokeWidth = 8f
        isAntiAlias = true
    }

    @FloatRange(from = 0.0, to = 1.0)
    var startY: Float? = null
        set(value) {
            field = value
            invalidate()
        }

    @FloatRange(from = 0.0, to = 1.0)
    var endY: Float? = null
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        startY ?: return
        endY ?: return
        //少しだけ線を伸ばさないとつなぎ目がダサくなるから延長してる
        val extensionLength = 1f.dp
        val movedProperty = { x: Float ->
            (height * startY!!) + (height * endY!! - height * startY!!) / width.toFloat() * x
        }

        val movedStartX = -extensionLength
        val movedEndX = width.toFloat() + extensionLength

        val movedStartY = movedProperty(movedStartX)
        val movedEndY = movedProperty(movedEndX)

        canvas?.drawLine(movedStartX, movedStartY, movedEndX, movedEndY, paint)
    }
}
