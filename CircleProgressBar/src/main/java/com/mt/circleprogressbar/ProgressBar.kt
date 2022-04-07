package com.mt.circleprogressbar

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * TODO: document your custom view class.
 */
class Progressbar : View , ValueAnimator.AnimatorUpdateListener{

    private var borderProgressBarSize: Float = 0f
    private var scaleColor: Int? = null
    private var valueColor: Int? = null
    private var valueProgressBar = 0f

    private lateinit var paintScale: Paint
    private lateinit var paintValue: Paint

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    @SuppressLint("Recycle")
    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ProgressBar, defStyle, 0
        )

        borderProgressBarSize =
            a.getDimensionPixelSize(R.styleable.ProgressBar_borderProgressBarSize, 20)
                .toFloat()
        scaleColor = a.getColor(R.styleable.ProgressBar_scaleColor, Color.GREEN)
        valueColor = a.getColor(R.styleable.ProgressBar_valueColor, Color.RED)
        valueProgressBar =
            IntToRadiusValue(a.getInt(R.styleable.ProgressBar_valueProgressBar, 25))

        paintScale = Paint()
        paintScale.color = scaleColor as Int
        paintScale.strokeWidth = borderProgressBarSize
        paintScale.isAntiAlias = true
        paintScale.style = Paint.Style.STROKE

        paintValue = Paint()
        paintValue.color = valueColor as Int
        paintValue.strokeWidth = borderProgressBarSize
        paintValue.style = Paint.Style.STROKE
        paintValue.strokeCap = Paint.Cap.ROUND

        a.recycle()

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = (this.width / 2) - 15F
        canvas.drawCircle(
            (this.width / 2).toFloat(),
            (this.height / 2).toFloat(), radius, paintScale
        )

        canvas.drawArc(
            RectF(
                this.width.toFloat() / 2 - radius,
                this.height.toFloat() / 2 - radius,
                this.width.toFloat() / 2 + radius,
                this.height.toFloat() / 2 + radius
            ), -90F, valueProgressBar, false, paintValue
        )


    }

    private fun IntToRadiusValue(progressValue: Int): Float {
        var value = progressValue
        if (progressValue > 100) {
            value = 100
        }
        return ((value * 360 / 100).toFloat())

    }

    fun SetProgressBarValue(updateValue: Int) {

        valueProgressBar = IntToRadiusValue(updateValue)
        this.invalidate();

    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {

        animation?.let {
            SetProgressBarValue(it.animatedValue as Int)
        }

    }
}