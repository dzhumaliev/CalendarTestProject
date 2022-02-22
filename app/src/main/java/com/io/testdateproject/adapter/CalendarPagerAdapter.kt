package com.io.testdateproject.adapter

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.io.testdateproject.interfaces.IOnClick
import com.io.testdateproject.view.CustomViewItem
import com.io.testdateproject.interfaces.IMyEventListener
import java.util.*


open class CalendarPagerAdapter(
    private val context: Context,
    private val baseCalendar: Calendar
) : PagerAdapter(), IMyEventListener {

    companion object {
        const val MAX_VALUE = 500
    }

    private var mIClick: IOnClick? = null

    override fun getCount(): Int = MAX_VALUE

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MONTH, (position - 250))
        }

        val view = CustomViewItem(
            context = context,
            cal = calendar
        )

        view.setEventListener(this)

        container.addView(
            view,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        return view
    }


    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = (view == `object`)

    fun getCalendar(position: Int): Calendar {

        return (baseCalendar.clone() as Calendar).apply {
            add(Calendar.MONTH, position - MAX_VALUE / 2)
        }
    }

    override fun onEventOccurred(cal: Date, dayOfWeek: String, number: Int) {
        mIClick?.onClick(cal, dayOfWeek, number)
    }

    fun setMyListener(mClickListener: IOnClick?) {
        this.mIClick = mClickListener
    }
}

data class Places(
    var xAxis: Int,
    var yAxis: Int,
    var number: Int,
    var isWeekend: Boolean,
    var dayOfWeek: String,
    var clicked: Boolean,
    var myPaint: Paint

)
