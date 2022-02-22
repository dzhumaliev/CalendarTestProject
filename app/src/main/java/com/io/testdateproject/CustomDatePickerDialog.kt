package com.io.testdateproject

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.io.testdateproject.databinding.LayoutCustomDialogBinding
import com.io.testdateproject.view.CalendarPagerAdapter
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

        binding.tvMonthYear.text =
            SimpleDateFormat("MMMM yyyy", Locale.GERMAN).format(calendar.time)
        binding.tvWeekDay.text = SimpleDateFormat("EEEE, dd", Locale.GERMAN).format(calendar.time)


        binding.monthsViewPager.apply {
            adapter = CalendarPagerAdapter(fragmentActivity, calendar)
            onCalendarChangeListener = {
                setDateHeader(it)
            }
//            onTestClick = {
//                setTimeText(it)
//            }


            onTestClick = { cal: Date, day: Int ->
                setTimeText(cal, day)
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
        binding.tvDate.text = getDate(calendar.time, "MMMM, yyyy")
    }

    private fun getDate(dateStr: Date, format: String): String {
        return SimpleDateFormat(format, Locale.GERMAN).format(dateStr)
    }

    private fun setTimeText(cal: Date, day: Int) {
        binding.tvMonthYear.text = SimpleDateFormat("MMMM yyyy", Locale.GERMAN).format(cal)
//        binding.tvWeekDay.text = SimpleDateFormat("EEEE, ", Locale.GERMAN).format(cal) + day


    }
}