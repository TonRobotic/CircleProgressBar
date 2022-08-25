package com.mt.circleprogressbar

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.random.Random

/**
 * TODO: document your custom view class.
 */
class Progressbar : View, ValueAnimator.AnimatorUpdateListener {

    private lateinit var colorList: IntArray
    private var borderProgressBarSize: Float = 0f
    private var scaleColor: Int? = null
    private var valueColor: Int? = null
    private var valueProgressBar = 0f
    private var scaleMode = ModeCirCle
    private var mixValueColor:Int = 0

    private lateinit var paintScale: Paint
    private lateinit var paintValue: Paint

    companion object {
        const val BOX_STROKE_WIDTH = 5.0f
        const val WidthFaceFrame = 34
        const val HeightFaceFrame = 22
        const val RadiusX = 34
        const val RadiusY = 22
        const val ModeCirCle = 0
        const val ModeOval = 1
    }

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

    @SuppressLint("Recycle", "CustomViewStyleable")
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
            intToRadiusValue(a.getInt(R.styleable.ProgressBar_valueProgressBar, 25))

        scaleMode = a.getInt(R.styleable.ProgressBar_modeProgressBar,1)
        mixValueColor = a.getResourceId(R.styleable.ProgressBar_mixColor,R.array.progressBarMixColor)

        if (mixValueColor != 0){
            colorList = resources.getIntArray(mixValueColor)
        }

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
        if (scaleMode == ModeCirCle){
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
        }else{
            canvas.drawArc(
                getRectFOval(), 0f, 360f, false, paintScale
            )

            canvas.drawArc(
                getRectFOval(), valueProgressBar - 90f, valueProgressBar, false, paintValue
            )
        }
    }

    private fun getRectFOval(): RectF {
        return RectF(
            width.div(2).toFloat() - percentageWidth(width = width.toFloat()),
            height.div(2).toFloat() - percentageHeight(height = height.toFloat()),
            width.div(2).toFloat() + percentageWidth(width = width.toFloat()),
            height.div(2).toFloat() + percentageHeight(height = height.toFloat())
        )
    }

    private fun percentageWidth(width: Float): Float {
        return (width * WidthFaceFrame) / 100
    }

    private fun percentageHeight(height: Float): Float {
        return (height * HeightFaceFrame) / 100
    }

    private fun intToRadiusValue(progressValue: Int): Float {
        var value = progressValue
        if (progressValue > 100) {
            value = 100
        }
        return ((value * 360 / 100).toFloat())

    }

    private fun setProgressBarValue(updateValue: Int) {
        valueProgressBar = intToRadiusValue(updateValue)
        if (updateValue == 100){
            if (colorList.isNotEmpty()){
                paintValue.color = colorList.random()
            }
        }
        this.invalidate()
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        animation?.let {
            setProgressBarValue(it.animatedValue as Int)
        }
    }
}