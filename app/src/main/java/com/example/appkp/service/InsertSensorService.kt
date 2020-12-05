package com.example.appkp.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.appkp.api.RetrofitBuilder
import com.example.appkp.model.sensors.SensorResponse
import com.example.appkp.util.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class InsertSensorService : IntentService("InsertSensorService") {


    companion object {
        const val ACTION_SEND_SENSOR_DATA = "com.example.appkp.service.action.SEND_SENSOR_DATA"


        const val EXTRA_SPO2 = "com.example.appkp.service.extra.SPO2"
        const val EXTRA_BPM = "com.example.appkp.service.extra.BPM"
        const val EXTRA_PI = "com.example.appkp.service.extra.PI"
        const val EXTRA_TOKEN = "com.example.appkp.service.extra.TOKEN"


        internal val TAG = InsertSensorService::class.java.simpleName
    }



    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent: Mulai.....")
        try {
            when (intent?.action) {
                ACTION_SEND_SENSOR_DATA -> {
                    val spo2Data = intent.getStringExtra(EXTRA_SPO2) as String
                    val bpmData = intent.getStringExtra(EXTRA_BPM) as String
                    val piData = intent.getStringExtra(EXTRA_PI) as String
                    val token = intent.getStringArrayExtra(EXTRA_TOKEN) as String
                    handleActionFooSendData(spo2Data, bpmData, piData, token)
                }
            }
            Log.d(TAG, "onHandleIntent: Selesai.....")
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }


    private fun handleActionFooSendData(spo2: String, bpm: String, pi: String, token: String) {

        RetrofitBuilder(Constant.BASE_URL).api.insertSensorData(bpm, spo2, pi, "Bearer $token")
            .enqueue(object : Callback<SensorResponse> {
                override fun onFailure(call: Call<SensorResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<SensorResponse>,
                    response: Response<SensorResponse>
                ) {
                    TODO("Not yet implemented")
                }

            })
        Log.d(TAG, "$spo2 $bpm $pi")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }
}