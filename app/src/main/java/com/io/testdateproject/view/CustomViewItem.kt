package com.io.testdateproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.io.testdateproject.R
import java.util.*


class CustomViewItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val cal: Calendar
) : View(context, attrs, defStyleAttr) {

    private var onDateChangeListener: OnDateChangeListener? = null

    private var widthSize = 0
    private var heightSize = 0
    private val listDays = arrayListOf<Places>()

    private val _paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        typeface = ResourcesCompat.getFont(context, R.font.montserrat_medium)
        color = Color.BLACK
        textSize = 24f
        textAlign = Paint.Align.CENTER

    }

    private val _paintWdText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        color = ContextCompat.getColor(context, R.color.yellow_FFDA5A)
        textSize = 24f
        textAlign = Paint.Align.CENTER
    }

    private val _paintYellowBack = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.yellow_FFDA5A)
        style = Paint.Style.FILL
    }

    private val _paintWeekBack = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        color = ContextCompat.getColor(context, R.color.black)
        textSize = 24f
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    private val _paintTranspBack = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.TRANSPARENT
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val xAxes = arrayListOf<Int>()
        val yAxes = arrayListOf<Int>()
        cal.firstDayOfWeek = Calendar.MONDAY
        cal.minimalDaysInFirstWeek = 1
        val weekInMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH)

        var xStepper = -27
        repeat(7) {
            xStepper += (widthSize / 7)
            xAxes.add(xStepper)
        }

        var yStepper = -25
        repeat(weekInMonth) {
            yStepper += (heightSize / weekInMonth)
            yAxes.add(yStepper)
        }
        canvas.apply {
            createText(listDays, xAxes, yAxes)
        }
    }

    private fun Canvas.createText(
        listOfPlaces: ArrayList<Places>,
        xAxes: ArrayList<Int>,
        yAxes: ArrayList<Int>
    ) {

        var yStep = 0

        for (i in 1..cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            cal.set(Calendar.DAY_OF_MONTH, i)
            when (cal.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> {
                    listOfPlaces.add(
                        Places(
                            xAxis = xAxes[0],
                            yAxis = yAxes[yStep],
                            i,
                            isWeekend = false,
                            clicked = false,
                            myPaint = _paintText
                        )
                    )
                }
                Calendar.TUESDAY -> {
                    listOfPlaces.add(
                        Places(
                            xAxis = xAxes[1],
                            yAxis = yAxes[yStep],
                            i,
                            isWeekend = false,
                            clicked = false,
                            myPaint = _paintText
                        )
                    )
                }
                Calendar.WEDNESDAY -> {
                    listOfPlaces.add(
                        Places(
                            xAxis = xAxes[2],
                            yAxis = yAxes[yStep],
                            i,
                            isWeekend = false,
                            clicked = false,
                            myPaint = _paintText
                        )
                    )
                }
                Calendar.THURSDAY -> {
                    listOfPlaces.add(
                        Places(
                            xAxis = xAxes[3],
                            yAxis = yAxes[yStep],
                            i,
                            false,
                            clicked = false,
                            myPaint = _paintText
                        )
                    )
                }
                Calendar.FRIDAY -> {
                    listOfPlaces.add(
                        Places(
                            xAxis = xAxes[4],
                            yAxis = yAxes[yStep],
                            i,
                            isWeekend = false,
                            clicked = false,
                            myPaint = _paintText
                        )
                    )
                }
                Calendar.SATURDAY -> {
                    listOfPlaces.add(
                        Places(
                            xAxis = xAxes[5],
                            yAxis = yAxes[yStep],
                            i,
                            isWeekend = true,
                            clicked = false,
                            myPaint = _paintWdText
                        )
                    )
                }
                Calendar.SUNDAY -> {
                    listOfPlaces.add(
                        Places(
                            xAxes[6],
                            yAxes[yStep],
                            i,
                            isWeekend = true,
                            clicked = false,
                            myPaint = _paintWdText
                        )
                    )
                    ++yStep
                }
            }
        }

        listOfPlaces.forEach {


            if (it.clicked) {
                drawCircle(
                    it.xAxis.toFloat(),
                    it.yAxis.toFloat(),
                    25f,
                    _paintYellowBack
                )

                if (it.isWeekend) {
                    drawText(
                        it.number.toString(),
                        it.xAxis.toFloat(),
                        it.yAxis.toFloat() + 8,
                        _paintWeekBack
                    )
                }

                Log.e("it.number", it.number.toString())
            } else {
                drawText(
                    it.number.toString(),
                    it.xAxis.toFloat(),
                    it.yAxis.toFloat() + 8,
                    it.myPaint
                )

                drawCircle(
                    it.xAxis.toFloat(),
                    it.yAxis.toFloat(),
                    25f,
                    _paintTranspBack
                )

            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_UP -> {

                listDays.forEach {
                    it.clicked = false
                    (it.xAxis until it.xAxis + 57).forEach { xIt ->
                        (it.yAxis until it.yAxis + 50).forEach { yIt ->
                            if (xIt == event.x.toInt() && yIt == event.y.toInt()) {

                                it.clicked = true
                                invalidate()

                                onDateChangeListener?.onChangeDate(it.number)
                            }
                        }
                    }
                }
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        widthSize = MeasureSpec.getSize(widthMeasureSpec)
        heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val finalMeasureSpecW =
            MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY)
        val finalMeasureSpecH =
            MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY)
        super.onMeasure(finalMeasureSpecW, finalMeasureSpecH)
    }
}