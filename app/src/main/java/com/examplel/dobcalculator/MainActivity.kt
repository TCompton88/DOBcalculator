package com.examplel.dobcalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Locale.ENGLISH

class MainActivity : AppCompatActivity() {

    private var dateChosen : TextView? = null
    private var ageInMins : TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectDate : Button = findViewById(R.id.selectDate)
        val resetButton : Button = findViewById(R.id.reset)
        dateChosen = findViewById(R.id.dateChosen)
        ageInMins = findViewById(R.id.ageInMins)

        resetButton.setOnClickListener {
            resetApp()
        }
        selectDate.setOnClickListener{
            clickedDate()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickedDate(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, dayOfMonth ->
                val selectedDate = "${selectedMonth+1}/${dayOfMonth}/$selectedYear"
                dateChosen?.text = selectedDate

                val sdf = SimpleDateFormat(
                    "MM/dd/yyyy",
                    ENGLISH
                )

                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMins = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMins = currentDate.time / 60000
                        val diffInMins = currentDateInMins - selectedDateInMins

                        ageInMins?.text = "$diffInMins"
                    }
                }
                //Toast.makeText(this,"Date Picker Worked", Toast.LENGTH_LONG).show()
            },
            year, month, day)
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }

    private fun resetApp(){
        ageInMins?.text = null
        dateChosen?.text = null
    }
}