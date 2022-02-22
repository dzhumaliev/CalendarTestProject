package com.io.testdateproject.interfaces

import java.util.*

interface IMyEventListener {
    fun onEventOccurred(cal: Date, dayOfWeek: String, number: Int)
}