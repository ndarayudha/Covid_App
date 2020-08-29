package com.example.appkp.formatter

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class MyXAxisFormatter : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val sdf = SimpleDateFormat("hh:mm", Locale.getDefault())

        return sdf.format(Date())
    }
}
