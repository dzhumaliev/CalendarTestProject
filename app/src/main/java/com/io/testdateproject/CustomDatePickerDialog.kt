package com.io.testdateproject

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.io.testdateproject.databinding.LayoutCustomDialogBinding
import com.io.testdateproject.adapter.CalendarPagerAdapter
import java.text.SimpleDateFormat
import java.util.*


class CustomDatePickerDialog @JvmOverloads constructor(
    private val fragmentActivity: FragmentActivity
) : AlertDialog(fragmentActivity) {

    private lateinit var binding: LayoutCustomDialogBinding

    private var calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutCustomDialogBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setDateHeader(calendar)
        setDateDesc(calendar)

        binding.monthsViewPager.apply {
            adapter = CalendarPagerAdapter(fragmentActivity, calendar)
            onCalendarChangeListener = {
                setDateHeader(it)
            }
            onTestClick = { cal: Date, dayOfWeek: String, day: Int ->
                setTimeText(cal, dayOfWeek, day)
            }
            binding.btnPrev.setOnClickListener {
                currentItem -= 1
            }
            binding.btnNext.setOnClickListener {
                currentItem += 1
            }
        }

        binding.btnAccept.setOnClickListener {
            cancel()
        }
        binding.btnDecline.setOnClickListener {
            cancel()
        }
    }

    private fun setDateHeader(calendar: Calendar) {
        binding.tvDate.text = getDate(calendar.time, DATE_HEADER_TYPE)
    }

    private fun setDateDesc(calendar: Calendar) {
        binding.tvMonthYear.text = getDate(calendar.time, DATE_DESC_TYPE)
        binding.tvWeekDay.text = getDate(calendar.time, DATE_WEEK_TYPE)
    }

    private fun getDate(dateStr: Date, format: String): String {
        return SimpleDateFormat(format, Locale.GERMAN).format(dateStr)
    }

    @SuppressLint("SetTextI18n")
    private fun setTimeText(cal: Date, dayOfWeek: String, day: Int) {
        binding.tvMonthYear.text = getDate(cal, DATE_DESC_TYPE)
        binding.tvWeekDay.text = "$dayOfWeek, $day"
    }

    companion object {
        const val DATE_HEADER_TYPE = "MMMM, yyyy"
        const val DATE_DESC_TYPE = "MMMM yyyy"
        const val DATE_WEEK_TYPE = "EEEE, dd"
    }
}