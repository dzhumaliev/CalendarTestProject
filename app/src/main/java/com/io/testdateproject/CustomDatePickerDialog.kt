package com.io.testdateproject

import android.app.AlertDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.io.testdateproject.databinding.LayoutCustomDialogBinding
import com.io.testdateproject.view.CalendarPagerAdapter
import com.io.testdateproject.view.OnDateChangeListener
import java.util.*


class CustomDatePickerDialog @JvmOverloads constructor(
    private val fragmentActivity: FragmentActivity
) : AlertDialog(fragmentActivity), OnDateChangeListener {

    private lateinit var binding: LayoutCustomDialogBinding

    private var calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutCustomDialogBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val monthName = DateUtils.formatDateTime(
            fragmentActivity, calendar.timeInMillis,
            DateUtils.FORMAT_ABBREV_MONTH or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NO_MONTH_DAY
        )

        setDateHeader(calendar)

        binding.monthsViewPager.apply {
            adapter = CalendarPagerAdapter(fragmentActivity, calendar)

            Log.e("monthName", monthName.toString())

            onCalendarChangeListener = {
                setDateHeader(it)
            }

            binding.btnPrev.setOnClickListener {
                currentItem -= 1
            }

            binding.btnNext.setOnClickListener {
                currentItem += 1
            }

            setOnDateChangeListener(this@CustomDatePickerDialog)

        }

        binding.btnAccept.setOnClickListener {
            this.cancel()
        }
        binding.btnDecline.setOnClickListener {
            this.cancel()
        }
    }

    private fun setDateHeader(calendar: Calendar) {
        binding.tvDate.text = DateUtils.formatDateTime(
            fragmentActivity, calendar.timeInMillis,
            DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NO_MONTH_DAY
        )
    }

    override fun onChangeDate(day: Int) {
        Log.e("ddaaaaa", day.toString())
    }
}