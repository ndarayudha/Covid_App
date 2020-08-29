package com.example.appkp.ui.dashboard.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appkp.R
import com.example.appkp.api.RetrofitBuilder
import com.example.appkp.formatter.MyValueFormarter
import com.example.appkp.formatter.MyXAxisFormatter
import com.example.appkp.model.thingspeak.ThingspeakResponse
import com.example.appkp.util.Constant
import com.example.appkp.util.Preferences
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {


    lateinit var piChart: LineChart
    lateinit var bpmChart: LineChart
    lateinit var lineDataSet: LineDataSet
    lateinit var iLineDataSet: ArrayList<ILineDataSet>
    lateinit var lineData: LineData
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var preference: Preferences


    companion object {
        var suhu: Long? = 0L
        var date = ""
        val degreePattern = (+0x00B0).toChar() + "C"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bpmChart = view.findViewById(R.id.lineChart_BPM)
        piChart = view.findViewById(R.id.lineChart_PI)

        preference = Preferences(view.context)
        val name = preference.getValue("name")
        tv_name.text = name


        progressBar2.apply {
            setProgressWithAnimation(97f, 1000)
        }


        btn_bpm_insert.setOnClickListener {
            insertBpmData()
        }

        btn_bpm_delete.setOnClickListener {
            deleteBpmData()
        }


        btn_pi_insert.setOnClickListener {
            insertPiData()
        }

        btn_pi_delete.setOnClickListener {
            deletePiData()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        simpleDateFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())


        lineDataSet = LineDataSet(null, null)
        iLineDataSet = ArrayList()
        lineData = LineData()


        bpmFormatter()
        piFormatter()
        bpmChartStyle()
        piChartStyle()

        insertBpmData()
        insertPiData()

        getThingspeakData()
    }


    private fun getThingspeakData() {

        RetrofitBuilder(Constant.BASE_THINGSPEAK_URL).api.getThingspeakData(10)
            .enqueue(object : Callback<ThingspeakResponse> {

                override fun onFailure(call: Call<ThingspeakResponse>, t: Throwable) {
                    Log.d("ThingspeakError", t.message)
                }

                override fun onResponse(
                    call: Call<ThingspeakResponse>,
                    response: Response<ThingspeakResponse>
                ) {
                    val result = response.body()!!.feeds
                    val size = response.body()?.feeds?.size

                    for (feeds in result) {
                        Log.d("ThingspeakData", "Data ke ${feeds.entryId}")
                        Log.d("ThingspeakData", "Spo2 = ${feeds.field1}")
                        Log.d("ThingspeakData", "BPM = ${feeds.field2}")
                        Log.d("ThingspeakData", "PI = ${feeds.field3}")
                        Log.d("ThingspeakData", "Latitude = ${feeds.field4}")
                        Log.d("ThingspeakData", "Longitude = ${feeds.field5}")
                    }

                    Log.d("ThingspeakSuccess", size.toString())
                    Log.d("ThingspeakSuccess", result.toString())
                }

            })
    }


    private fun insertBpmData() {
        val dataVals = ArrayList<Entry>()
        dataVals.add(Entry(1f, 10f))
        dataVals.add(Entry(2f, 12f))
        dataVals.add(Entry(3f, 8f))
        dataVals.add(Entry(4f, 19f))
        dataVals.add(Entry(5f, 21f))
        dataVals.add(Entry(6f, 29f))
        dataVals.add(Entry(7f, 6f))
        dataVals.add(Entry(8f, 10f))

        showBpmChart(dataVals)
    }

    private fun insertPiData() {
        val dataVals = ArrayList<Entry>()
        dataVals.add(Entry(1f, 10f))
        dataVals.add(Entry(2f, 12f))
        dataVals.add(Entry(3f, 8f))
        dataVals.add(Entry(4f, 19f))
        dataVals.add(Entry(5f, 21f))
        dataVals.add(Entry(6f, 29f))
        dataVals.add(Entry(7f, 6f))
        dataVals.add(Entry(8f, 10f))

        showPiChart(dataVals)
    }


    private fun deleteBpmData() {
        bpmChart.clear()
        lineDataSet.clear()
    }

    private fun deletePiData() {
        piChart.clear()
        lineDataSet.clear()
    }


    private fun showBpmChart(dataVals: ArrayList<Entry>) {
        lineDataSet.values = dataVals
        lineDataSet.label = "Data BPM"
        iLineDataSet.clear()
        iLineDataSet.add(lineDataSet)
        lineData = LineData(iLineDataSet)
        bpmChart.clear()
        bpmChart.data = lineData
        bpmChart.invalidate()
    }

    private fun showPiChart(dataVals: ArrayList<Entry>) {
        lineDataSet.values = dataVals
        lineDataSet.label = "Data PI"
        iLineDataSet.clear()
        iLineDataSet.add(lineDataSet)
        lineData = LineData(iLineDataSet)
        piChart.clear()
        piChart.data = lineData
        piChart.invalidate()
    }


    private fun bpmFormatter() {
        // init formatter
        bpmChart.axisLeft.valueFormatter = MyValueFormarter()
//        lineDataSet.valueFormatter = MyValueFormarter()
        bpmChart.xAxis.valueFormatter = MyXAxisFormatter()
    }

    private fun piFormatter() {
        // init formatter
        piChart.axisLeft.valueFormatter = MyValueFormarter()
//        lineDataSet.valueFormatter = MyValueFormarter()
        piChart.xAxis.valueFormatter = MyXAxisFormatter()
    }


    private fun bpmChartStyle() {
        bpmChart.setNoDataTextColor(Color.BLACK)
        bpmChart.setDrawBorders(true)
        bpmChart.isScaleYEnabled = false
        bpmChart.isDoubleTapToZoomEnabled = false
        lineDataSet.highLightColor = Color.RED
        lineDataSet.highlightLineWidth = 1f
        lineDataSet.color = Color.BLUE
        lineDataSet.circleHoleColor = Color.BLACK
        lineDataSet.lineWidth = 2f
        lineDataSet.valueTextSize = 10f
        lineDataSet.valueTextColor = Color.BLACK
    }


    private fun piChartStyle() {
        bpmChart.setNoDataTextColor(Color.BLACK)
        bpmChart.setDrawBorders(true)
        bpmChart.isScaleYEnabled = false
        bpmChart.isDoubleTapToZoomEnabled = false
        lineDataSet.highLightColor = Color.RED
        lineDataSet.highlightLineWidth = 1f
        lineDataSet.color = Color.BLUE
        lineDataSet.circleHoleColor = Color.BLACK
        lineDataSet.lineWidth = 2f
        lineDataSet.valueTextSize = 10f
        lineDataSet.valueTextColor = Color.BLACK
    }


}







