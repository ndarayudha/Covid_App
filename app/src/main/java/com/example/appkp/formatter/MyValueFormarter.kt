package com.example.appkp.formatter

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter

class MyValueFormarter : ValueFormatter() {

    override fun getPointLabel(entry: Entry?): String {
        return "${entry?.y} " + (+0x00B0).toChar() + "C"
    }

}
