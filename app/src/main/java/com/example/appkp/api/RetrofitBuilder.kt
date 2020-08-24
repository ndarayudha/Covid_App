package com.example.appkp.api

import com.example.appkp.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {


    private val retrofit by lazy {
        // create interceptor
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        // create client
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // create retrofit
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }

    val api by lazy {
        retrofit.create(API::class.java)
    }
}
