package com.example.appkp.ui.dashboard.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appkp.R
import com.example.appkp.R.color.indikator_spo2_cukup
import com.example.appkp.R.color.indikator_spo2_kurang
import com.example.appkp.api.RetrofitBuilder
import com.example.appkp.formatter.MyValueFormarter
import com.example.appkp.formatter.MyXAxisFormatter
import com.example.appkp.model.sensors.SensorResponse
import com.example.appkp.util.Constant
import com.example.appkp.util.Preferences
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {


    lateinit var piChart: LineChart
    lateinit var bpmChart: LineChart
    lateinit var piLineDataSet: LineDataSet
    lateinit var bpmLineDataSet: LineDataSet
    lateinit var piILineDataSet: ArrayList<ILineDataSet>
    lateinit var bpmILineDataSet: ArrayList<ILineDataSet>
    lateinit var piLineData: LineData
    lateinit var bpmLineData: LineData
    lateinit var preference: Preferences
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference


    var spo2: String = ""
    var bpm: String = ""
    var pi: String = ""


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


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("pulse_oximetry")


        preference = Preferences(view.context)
        val name = preference.getValue("name")
        tv_name.text = name


        btn_pi_delete.setOnClickListener {
            databaseReference.removeValue()
            setProgressBar(0f)
            bpmChart.clear()
            bpmChart.invalidate()
            piChart.clear()
            piChart.invalidate()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        piLineDataSet = LineDataSet(null, null)
        piILineDataSet = ArrayList()
        bpmLineDataSet = LineDataSet(null, null)
        bpmILineDataSet = ArrayList()
        piLineData = LineData()
        bpmLineData = LineData()


        bpmChartStyle()
        bpmFormatter()
        piChartStyle()
        piFormatter()


        getFirebaseData()
        updateDataSensor(spo2, bpm, pi)

    }

    private fun getFirebaseData() = CoroutineScope(Dispatchers.IO).launch {

        databaseReference.child("spo2").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("DataFirebase", databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (myDataSnapshot in dataSnapshot.children) {

                        val dataPoint = myDataSnapshot.getValue(String::class.java)
                        val filterEscapeSequence = dataPoint?.split(Regex("[\n\r]"))
                        val spo2Data = filterEscapeSequence!![0]
                        spo2 = spo2Data


                        if (spo2Data == "") {
                            setProgressBar(0f)
                        } else {
                            setProgressBar(spo2Data.toFloat())
                        }
                    }
                }
            }
        })


        databaseReference.child("bpm").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("DataFirebase", databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataBpm = ArrayList<Entry>()
                var xAxis = 0

                if (dataSnapshot.hasChildren()) {

                    for (myDataSnapshot in dataSnapshot.children) {

                        val dataPoint = myDataSnapshot.getValue(String::class.java)
                        val filterEscapeSequence = dataPoint?.split(Regex("[\n\r]"))
                        val bpmData = filterEscapeSequence!![0]
                        bpm = bpmData


                        if (bpmData == "") {
                            Log.d("DataFirebase", "Data Empty")
                        } else {
                            dataBpm.add(Entry(xAxis.toFloat(), bpmData.toFloat()))
                        }

                        xAxis++
                    }

                    showBpmChart(dataBpm)
                }
            }
        })


        databaseReference.child("pi").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("DataFirebase", databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataPi = ArrayList<Entry>()
                var xAxis = 0

                if (dataSnapshot.hasChildren()) {


                    for (myDataSnapshot in dataSnapshot.children) {

                        val dataPoint = myDataSnapshot.getValue(String::class.java)
                        val filterEscapeSequence = dataPoint?.split(Regex("[\n\r]"))
                        val piData = filterEscapeSequence!![0]
                        pi = piData


                        if (piData == "") {
                            Log.d("DataFirebase", "Data Empty")
                        } else {
                            dataPi.add(Entry(xAxis.toFloat(), piData.toFloat()))
                        }

                        xAxis++
                    }

                    showPiChart(dataPi)
                }
            }
        })
    }


    private fun updateDataSensor(spo2: String, bpm: String, pi: String) = CoroutineScope(Dispatchers.IO).launch {

        val token = preference.getValue("token")

        RetrofitBuilder(Constant.BASE_URL).api.updateSensor(spo2, bpm, pi, "bearer $token")
            .enqueue(object :
                Callback<SensorResponse> {

                override fun onFailure(call: Call<SensorResponse>, t: Throwable) {
                    Log.d("update sensor", t.toString())
                }

                override fun onResponse(
                    call: Call<SensorResponse>,
                    response: Response<SensorResponse>
                ) {
                    Log.d("update sensor", response.toString())
                }
            })
    }

    // setup progerssbar
    private fun setProgressBar(value: Float) {
        progressSpo2Bar?.apply {
            if (value <= 50) {
                progressBarColor = resources.getColor(indikator_spo2_kurang)
                setProgressWithAnimation(value, 1)
                tv_spo2_value?.text = "$value %"
            } else {
                progressBarColor = resources.getColor(indikator_spo2_cukup)
                setProgressWithAnimation(value, 1)
                tv_spo2_value?.text = "$value %"
            }
        }
    }


    private fun showBpmChart(dataVals: ArrayList<Entry>) = CoroutineScope(Dispatchers.IO).launch {
        bpmLineDataSet.values = dataVals
        bpmLineDataSet.label = "Data BPM"
        bpmILineDataSet.clear()
        bpmILineDataSet.add(bpmLineDataSet)
        bpmLineData = LineData(bpmILineDataSet)
        bpmChart.clear()
        bpmChart.data = bpmLineData
        bpmChart.invalidate()
    }

    private fun showPiChart(dataVals: ArrayList<Entry>) = CoroutineScope(Dispatchers.IO).launch {
        piLineDataSet.values = dataVals
        piLineDataSet.label = "Data PI"
        piILineDataSet.clear()
        piILineDataSet.add(piLineDataSet)
        piLineData = LineData(piILineDataSet)
        piChart.clear()
        piChart.data = piLineData
        piChart.invalidate()
    }

    private fun bpmFormatter() = CoroutineScope(Dispatchers.Default).launch {
        // init formatter
        bpmChart.xAxis.valueFormatter = MyXAxisFormatter()
    }

    private fun piFormatter() = CoroutineScope(Dispatchers.Default).launch {
        // init formatter
        piChart.xAxis.valueFormatter = MyXAxisFormatter()
    }

    private fun bpmChartStyle() = CoroutineScope(Dispatchers.Default).launch {
        bpmChart.setNoDataTextColor(Color.BLACK)
        bpmChart.setDrawBorders(true)
        bpmChart.isScaleYEnabled = false
        bpmChart.isDoubleTapToZoomEnabled = false
        bpmLineDataSet.highLightColor = Color.RED
        bpmLineDataSet.highlightLineWidth = 1f
        bpmLineDataSet.color = Color.BLUE
        bpmLineDataSet.circleHoleColor = Color.BLACK
        bpmLineDataSet.lineWidth = 2f
        bpmLineDataSet.valueTextSize = 10f
        bpmLineDataSet.valueTextColor = Color.BLACK
    }

    private fun piChartStyle() = CoroutineScope(Dispatchers.Default).launch {
        piChart.setNoDataTextColor(Color.BLACK)
        piChart.setDrawBorders(true)
        piChart.isScaleYEnabled = false
        piChart.isDoubleTapToZoomEnabled = false
        piLineDataSet.highLightColor = Color.RED
        piLineDataSet.highlightLineWidth = 1f
        piLineDataSet.color = Color.BLUE
        piLineDataSet.circleHoleColor = Color.BLACK
        piLineDataSet.lineWidth = 2f
        piLineDataSet.valueTextSize = 10f
        piLineDataSet.valueTextColor = Color.BLACK
    }
}







