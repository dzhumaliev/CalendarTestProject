package com.io.testdateproject.view

import java.util.*

interface IMyEventListener {
    fun onEventOccurred(cal: Date, number: Int)
}

interface IOnClick {
    fun onClick(cal: Date, number: Int)
}