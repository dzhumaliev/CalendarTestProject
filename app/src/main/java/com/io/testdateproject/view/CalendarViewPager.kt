package com.io.testdateproject.view

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.util.*

open class CalendarViewPager(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs), IOnClick {

    var onCalendarChangeListener: ((Calendar) -> Unit)? = null
    var onTestClick: ((Date, String, Int) -> Any)? = null

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)

        if (adapter is CalendarPagerAdapter) {
            this.clearOnPageChangeListeners()

            adapter.setMyListener(this)

            offscreenPageLimit = 0
            setCurrentItem(CalendarPagerAdapter.MAX_VALUE / 2, false)
            this.addOnPageChangeListener(pageChangeListener)
        }
    }

    private val pageChangeListener = object : OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            val calendar = (adapter as? CalendarPagerAdapter)?.getCalendar(position) ?: return
            onCalendarChangeListener?.invoke(calendar)
        }
    }

    override fun onClick(cal: Date,dayOfWeek: String, number: Int) {
        onTestClick?.invoke(cal, dayOfWeek, number)
    }
}