package com.io.testdateproject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_test).setOnClickListener {

            val dialog = CustomDatePickerDialog(this)
            dialog.show()
        }
    }
}