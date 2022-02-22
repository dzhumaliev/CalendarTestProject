package com.io.testdateproject.view

import java.util.*

interface IMyEventListener {
    fun onEventOccurred(cal: Date, dayOfWeek: String, number: Int)
}

interface IOnClick {
    fun onClick(cal: Date, dayOfWeek: String, number: Int)
}